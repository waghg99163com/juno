package com.juno.gateway.constant;

/**
 * @author: yuki
 * @date: 2020/4/19
 * @description: 网关服务常量类
 */
public interface Constant {

    /**
     * 熔断返回提示
     */
    String HYSTRIX_MSG = "服务暂时不可用";

    /**
     * Feign熔断返回提示
     */
    String SERVICE_CALL_ERROR = "服务调用异常";

    /**
     * 服务名称
     */
    String WEB_SERVICE = "WEB";
}
