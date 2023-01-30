package example.spring.data.middleware.flyway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class DataFlywayApplication implements CommandLineRunner {

    private final UserDao userDAO;

    public static void main(String[] args) {
        SpringApplication.run(DataFlywayApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<User> list = userDAO.list();
        list.forEach(user -> {
            log.info(user.toString());
        });
    }

}
