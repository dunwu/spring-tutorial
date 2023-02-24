package example.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebUIEasyuiApplication {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(WebUIEasyuiApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new User("张三", 18, "北京", "xxx@163.com"));
            repository.save(new User("李四", 18, "上海", "xxx@163.com"));
            repository.save(new User("王五", 18, "南京", "xxx@163.com"));
            repository.save(new User("赵六", 18, "杭州", "xxx@163.com"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (User customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            repository.findById(1L).ifPresent(customer -> {
                log.info("Customer found with findById(1L):");
                log.info("--------------------------------");
                log.info(customer.toString());
                log.info("");
            });

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            User user = repository.findUserByName("张三");
            log.info(user.toString());
        };
    }

}
