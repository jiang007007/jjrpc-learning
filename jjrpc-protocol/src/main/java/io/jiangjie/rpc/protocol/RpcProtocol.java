package io.jiangjie.rpc.protocol;

import io.jiangjie.rpc.protocol.header.RpcHeader;

import java.io.Serializable;

/**
 * 协议体
 *   包括:头部
 *      :传输的数据体
 * @param <T>
 */
public class RpcProtocol<T> implements Serializable {
    /**
     * 消息头
     */
    private RpcHeader header;
    /**
     * 消息体
     */
    private T body;

    public RpcHeader getHeader() {
        return header;
    }

    public void setHeader(RpcHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
