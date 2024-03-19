package io.jiangjie.rpc.annotation;

import org.jiangjie.rpc.constants.RpcConstants;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务提供者注解
 *  使用方式  标注到服务实现类上,如果某个接口的实现类被标注了该注解,则这个接口与实现类被发布到注册中心
 */
@Retention(RetentionPolicy.RUNTIME)//注解的生命周期
@Target(ElementType.FIELD)//注解的作用域
public @interface RpcService {
    /**
     * 接口的class
     */
    Class<?> interfaceClass()default void.class;
    /**
     * 接口名称
     */
    String interfaceName() default "";

    /**
     * 版本号
     */
    String version() default RpcConstants.RPC_COMMON_DEFAULT_VERSION;

    /**
     * 服务分组,默认为空
     */
    String group() default RpcConstants.RPC_COMMON_DEFAULT_GROUP;

    /**
     * 心跳间隔时间，默认30s
     */
    int heartbeatInterval() default RpcConstants.RPC_COMMON_DEFAULT_HEARTBEATINTERVAL;

    /**
     * 扫描空闲链接间隔时间
     */
    int scanNotActiveChannelInterval() default RpcConstants.RPC_COMMON_DEFAULT_SCANNOTACTIVECHANNELINTERVAL;
}
