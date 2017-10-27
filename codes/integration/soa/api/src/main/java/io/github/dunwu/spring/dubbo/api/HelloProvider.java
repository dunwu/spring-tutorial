package io.github.dunwu.spring.dubbo.api;

/**
 * @author Zhang Peng
 */
public interface HelloProvider {

    /**
     * 定义接口
     * @param name
     * @return
     */
    String sayHello(String name);
}
