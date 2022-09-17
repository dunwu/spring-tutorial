package io.github.dunwu.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-06
 */
@SpringBootApplication
public class SpringBootZooKeeperApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootZooKeeperApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // ZooKeeper zooKeeper = new ZooKeeper();
    }

}
