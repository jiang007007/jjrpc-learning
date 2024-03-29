package io.jiangjie.rpc.reflect.api;

/**
 * 反射方法的调用接口
 */
public interface ReflectInvoker {

    /**
     * 调用真实方法的SPI通过接口
     * @param serviceBean
     * @param serviceClass
     * @param method
     * @param parameterType
     * @param parameters
     * @return
     * @throws Throwable
     */
    Object invoker(Object serviceBean,Class<?> serviceClass,String method,Class<?>[] parameterType,Object[] parameters)throws Throwable;

}
