package io.jiangjie.rpc.codec;

import io.jiangjie.rpc.Codec;
import io.jiangjie.rpc.common.utils.SerializationUtils;
import io.jiangjie.rpc.protocol.RpcProtocol;
import io.jiangjie.rpc.protocol.enumeration.RpcType;
import io.jiangjie.rpc.protocol.header.RpcHeader;
import io.jiangjie.rpc.protocol.request.RpcRequest;
import io.jiangjie.rpc.protocol.respone.RpcResponse;
import io.jiangjie.rpc.serialization.api.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import jdk.internal.foreign.CABI;
import org.jiangjie.rpc.constants.RpcConstants;

import java.util.List;

/**
 * 解码器
 */
public class RpcDecoder extends ByteToMessageDecoder implements Codec {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < RpcConstants.HEADER_TOTAL_LEN) {
            return;
        }
        byteBuf.markReaderIndex();
        short magic = byteBuf.readShort();
        if (magic!=RpcConstants.MAGIC){
            throw new IllegalArgumentException(String.format("magic number us illegal, %s",magic));
        }
        byte msgType = byteBuf.readByte();
        byte status = byteBuf.readByte();
        long requestId = byteBuf.readLong();

        ByteBuf serializationTypeByteBuf = byteBuf.readBytes(RpcConstants.NAX_SERIALIZATION_TYPE_COUNR);
        String serializationType = SerializationUtils.subString(serializationTypeByteBuf.toString(CharsetUtil.UTF_8));

        int dataLength = byteBuf.readInt();

        //还未读完
        if (byteBuf.readableBytes() < dataLength){
            byteBuf.resetReaderIndex();//重置都指针
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        //获取到消息类型
        RpcType msgTypeEnum = RpcType.findByType(msgType);

        if (msgTypeEnum == null){
            return;
        }

        //创建消息
        RpcHeader header = new RpcHeader();
        header.setMagic(magic);
        header.setStatus(status);
        header.setMsgType(msgType);
        header.setSerializationType(serializationType);
        header.setMsgLen(dataLength);
        //获取序列化具体实现类
        Serialization serialization = getSerialization(serializationType);

        switch (msgTypeEnum){
            case REQUEST:
                //服务消费者发送给服务提供者的心跳数据
            case HEARTBEAT_FROM_CONSUMER:
                //服务提供者发送给服务消费者的心跳数据
            case HEARTBEAT_TO_PROVIDER:
                //反序列请求消息
                RpcRequest request = serialization.deserialize(data, RpcRequest.class);
                if (request!=null){
                    RpcProtocol<RpcRequest> protocol = new RpcProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(request);
                    list.add(protocol);
                }
                break;
            case RESPONSE:
                //服务提供者响应服务消费者的心跳数据
            case HEARTBEAT_TO_CONSUMER:
                //服务消费者响应服务提供者的心跳数据
            case HEARTBEAT_FROM_PROVIDER:
                RpcResponse response = serialization.deserialize(data, RpcResponse.class);
                if (response!=null){
                    RpcProtocol<RpcResponse> protocol = new RpcProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(response);
                    list.add(protocol);
                }
                break;
        }

    }
}
