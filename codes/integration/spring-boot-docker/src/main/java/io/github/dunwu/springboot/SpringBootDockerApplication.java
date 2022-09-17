package io.github.dunwu.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@SpringBootApplication
public class SpringBootDockerApplication implements CommandLineRunner, EnvironmentAware {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDockerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("============== ARGUMENTS ==============");
        if (args != null && args.length > 0) {
            for (String arg : args) {
                System.out.println(arg);
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {

        ConfigurableEnvironment configEnv = (ConfigurableEnvironment) environment;

        System.out.println("============== SYSTEM PROPERTIES ==============");
        printMap(configEnv.getSystemProperties());

        System.out.println();

        System.out.println("============== SYSTEM ENVIRONMENTS ==============");
        printMap(configEnv.getSystemEnvironment());
    }

    private void printMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue().toString());
        }
    }

    @RequestMapping("/")
    public String home() {
        return "Running in docker.";
    }

}
