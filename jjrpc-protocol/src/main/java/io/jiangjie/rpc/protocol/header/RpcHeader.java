package io.jiangjie.rpc.protocol.header;

import java.io.Serializable;
  /*
    +---------------------------------------------------------------+
    | 魔数 2byte | 报文类型 1byte | 状态 1byte |     消息 ID 8byte      |
    +---------------------------------------------------------------+
    |           序列化类型 16byte      |        数据长度 4byte          |
    +---------------------------------------------------------------+
    固定32位

    */

public class RpcHeader implements Serializable {
    /**
     * 魔数 2字节
     */
    private short magic;
    /**
     * 报文类型 1字节
     */
    private byte msgType;
    /**
     * 状态 1字节
     */
    private byte status;

    /**
     * 消息 ID 8字节
     */
    private long requestId;

    /**
     * 序列化类型16字节，不足16字节后面补0，约定序列化类型长度最多不能超过16
     */
    private String serializationType;

    /**
     * 消息长度 4字节
     */
    private int msgLen;


    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getSerializationType() {
        return serializationType;
    }

    public void setSerializationType(String serializationType) {
        this.serializationType = serializationType;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getMsgLen() {
        return msgLen;
    }

    public void setMsgLen(int msgLen) {
        this.msgLen = msgLen;
    }

}
