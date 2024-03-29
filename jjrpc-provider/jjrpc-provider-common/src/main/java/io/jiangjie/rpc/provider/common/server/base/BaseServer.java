package io.jiangjie.rpc.provider.common.server.base;

import io.jiangjie.rpc.codec.RpcDecoder;
import io.jiangjie.rpc.codec.RpcEncoder;
import io.jiangjie.rpc.provider.common.handler.RpcProviderHandler;
import io.jiangjie.rpc.provider.common.server.api.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.jiangjie.rpc.constants.RpcConstants;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseServer implements Server {

    //心跳间隔时间，默认30秒
    private int heartbeatInterval = 30000;

    private String reflectType;


    //是否开启结果缓存
    private boolean enableResultCache;

    //结果缓存过期时长，默认5秒
    private int resultCacheExpire = 5000;
    //核心线程数
    private int corePoolSize;

    //最大线程数
    private int maximumPoolSize;

    //最大连接限制
    private int maxConnections;

    //拒绝策略类型
    private String disuseStrategyType;

    //是否开启数据缓冲
    private boolean enableBuffer;
    //缓冲区大小
    private int bufferSize;
    //是否开启限流
    private boolean enableRateLimiter;
    //限流类型
    private String rateLimiterType;
    //在milliSeconds毫秒内最多能够通过的请求个数
    private int permits;
    //毫秒数
    private int milliSeconds;
    //当限流失败时的处理策略
    private String rateLimiterFailStrategy;
    //是否开启熔断策略
    private boolean enableFusing;
    //熔断规则标识
    private String fusingType;
    //在fusingMilliSeconds毫秒内触发熔断操作的上限值
    private double totalFailure;
    //熔断的毫秒时长
    private int fusingMilliSeconds;
    //异常后置处理器标识
    private String exceptionPostProcessorType;
    //存储的是实体类关系
    protected Map<String, Object> handlerMap = new HashMap<>();

    //端口号
    private int port = 27110;

    //启动netty
    @Override
    public void startNettyServer() {
        //首先启动心跳线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(EpollServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()//获取当前流水线
                                    .addLast(RpcConstants.CODEC_DECODER, new RpcDecoder())
                                    .addLast(RpcConstants.CODEC_ENCODER, new RpcEncoder())
                                    .addLast(RpcConstants.CODEC_SERVER_IDLE_HANDLER, new IdleStateHandler(0, 0, heartbeatInterval, TimeUnit.MILLISECONDS))
                                    .addLast(RpcConstants.CODEC_HANDLER, new RpcProviderHandler(reflectType, enableResultCache, resultCacheExpire, corePoolSize, maximumPoolSize, maxConnections,
                                            disuseStrategyType, enableBuffer, bufferSize, enableRateLimiter, rateLimiterType, permits, milliSeconds, rateLimiterFailStrategy, enableFusing, fusingType,
                                            totalFailure, fusingMilliSeconds, exceptionPostProcessorType, handlerMap));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.printf("Server started on port= %s", port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        ByteBuffer b = ByteBuffer.allocate(4);
        System.out.println(b.hasRemaining());
        final Object objA = new Object();
        final Object objB = new Object();
        new Thread(() -> {
            synchronized (objA) {
                System.out.println(Thread.currentThread().getName() + "自己持有A锁，希望获得B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objB) {
                    System.out.println(Thread.currentThread().getName() + "成功获取到了B锁");
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (objB) {
                System.out.println(Thread.currentThread().getName() + "自己持有B锁，希望获得A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objA) {
                    System.out.println(Thread.currentThread().getName() + "成功获取到了A锁");
                }
            }
        }).start();
    }
}
