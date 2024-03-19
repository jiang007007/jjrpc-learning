package io.jiangjie.rpc.annotation;

import com.sun.glass.utils.NativeLibLoader;
import org.jiangjie.rpc.constants.RpcConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务消费者注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcReference {
    /**
     * 版本号
     */
    String version() default RpcConstants.RPC_COMMON_DEFAULT_VERSION;

    /**
     * 注册类型 zl  nacos ...
     */
    String registryType() default RpcConstants.RPC_REFERENCE_DEFAULT_REGISTRYTYPE;

    /**
     * 注册地址
     */
    String registryAddress() default RpcConstants.RPC_REFERENCE_DEFAULT_REGISTRYADDRESS;

    /**
     * 负载均衡 默认负载均衡
     */
    String loadBalanceType() default RpcConstants.RPC_REFERENCE_DEFAULT_LOADBALANCETYPE;

    /**
     * 服务分组
     */
    String group() default RpcConstants.RPC_COMMON_DEFAULT_GROUP;

    /**
     * 序列哈类型  使用那种序列化放肆  默认:protostuff
     */
    String serializationType() default RpcConstants.RPC_REFERENCE_DEFAULT_SERIALIZATIONTYPE;

    /**
     * 是否异步
     */
    boolean async() default  false;

    /**
     * 是否单向调用
     */
    boolean oneway() default false;

    /**
     * 反射类型
     */
    String reflectType() default RpcConstants.DEFAULT_REFLECT_TYPE;

    /**
     * 默认并发线程池核心线程数
     */
    int corePoolSize() default RpcConstants.DEFAULT_CORE_POOL_SIZE;
}
