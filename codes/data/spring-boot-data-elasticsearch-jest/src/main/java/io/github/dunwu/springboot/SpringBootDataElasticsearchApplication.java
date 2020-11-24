package io.github.dunwu.springboot;

import io.github.dunwu.spring.data.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;

@SpringBootApplication(exclude = { ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class })
public class SpringBootDataElasticsearchApplication implements CommandLineRunner {

    private final UserRepository repository;

    public SpringBootDataElasticsearchApplication(UserRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataElasticsearchApplication.class);
    }

    @Override
    public void run(String... args) {
        System.out.println("[index = user] 的文档数：" + repository.count());
    }

}
