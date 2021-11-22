package io.github.dunwu.spring.dubbo.provider;

import com.alibaba.dubbo.rpc.RpcContext;
import io.github.dunwu.spring.dubbo.api.HelloProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zhang Peng
 */
public class HelloProviderImpl implements HelloProvider {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name
            + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }

}
