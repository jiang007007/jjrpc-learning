package io.jiangjie.rpc.codec;

import io.jiangjie.rpc.Codec;
import io.jiangjie.rpc.common.utils.SerializationUtils;
import io.jiangjie.rpc.protocol.RpcProtocol;
import io.jiangjie.rpc.protocol.header.RpcHeader;
import io.jiangjie.rpc.serialization.api.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * 编码器
 */
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> implements Codec {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol<Object> objectRpcProtocol, ByteBuf byteBuf) throws Exception {
        RpcHeader header = objectRpcProtocol.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        String serializationType = header.getSerializationType();

        Serialization serialization = getSerialization(serializationType);

        byteBuf.writeBytes(SerializationUtils.paddingString(serializationType).getBytes(StandardCharsets.UTF_8));
        //序列化
        byte[] data = serialization.serialize(objectRpcProtocol.getBody());
        byteBuf.writeInt(data.length);//消息长度
        byteBuf.writeBytes(data);//消息体
        header.setMsgLen(data.length);

    }
}
