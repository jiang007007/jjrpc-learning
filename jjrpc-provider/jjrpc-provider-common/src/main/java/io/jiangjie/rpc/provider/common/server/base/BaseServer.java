package io.jiangjie.rpc.provider.common.server.base;

import io.jiangjie.rpc.provider.common.server.api.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.TimeUnit;

public class BaseServer implements Server {
    //启动netty
    @Override
    public void startNettyServer() {
        //首先启动心跳线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
//            bootstrap.group(bossGroup,workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline()
//                                    .addLast()
//                        }
//                    })
        }catch (Exception e){

        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) {
      final Object objA = new Object();
      final Object objB = new Object();
      new Thread(()->{
            synchronized (objA){
                System.out.println(Thread.currentThread().getName()+"自己持有A锁，希望获得B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (objB){
                    System.out.println(Thread.currentThread().getName()+"成功获取到了B锁");
                }
            }
      }).start();
        new Thread(()->{
            synchronized (objB){
                System.out.println(Thread.currentThread().getName()+"自己持有B锁，希望获得A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (objA){
                    System.out.println(Thread.currentThread().getName()+"成功获取到了A锁");
                }
            }
        }).start();
    }
}
