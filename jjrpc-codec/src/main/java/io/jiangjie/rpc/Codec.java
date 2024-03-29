package io.jiangjie.rpc;

import io.jiangjie.rpc.serialization.api.Serialization;

public interface Codec {
    /**
     * 根据serializationType通过SPI获取序列化句柄
     * @param serializationType 序列化方式
     * @return Serialization对象
     */
    default Serialization getSerialization(String serializationType){
        return new Serialization() {
            @Override
            public <T> byte[] serialize(T obj) {
                return new byte[0];
            }

            @Override
            public <T> T deserialize(byte[] data, Class<T> cls) {
                return null;
            }
        };
    }

    default void postFlowProcessor(){}
}
