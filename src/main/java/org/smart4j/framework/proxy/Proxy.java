package org.smart4j.framework.proxy;

/**
 * 代理接口
 * Created by xms on 2017/3/7 0007.
 */
public interface Proxy {

    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
