package example.spring.core.aop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 读取 properties 或 yml 配置中的 author.name 参数，打印欢迎内容
 */
@Service
public class WelcomeService {

    @Value("${author.name:Unknow}")
    private String name;

    @Value("${throw.exception:false}")
    private boolean throwException;

    public void getException() throws Exception {
        if (throwException) {
            throw new Exception("throw.exception = true");
        }
    }

    public String getMessage() {
        return "Welcome " + this.name;
    }

    @MethodLog
    public void print(String info) {
        System.out.println("info = " + info);
    }

}
