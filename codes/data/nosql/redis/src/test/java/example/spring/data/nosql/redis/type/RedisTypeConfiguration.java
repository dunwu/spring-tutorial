package example.spring.data.nosql.redis.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.annotation.PreDestroy;

/**
 * @author Christoph Strobl
 */
@SpringBootApplication
public class RedisTypeConfiguration {

    @Autowired
    private RedisConnectionFactory factory;

    /**
     * Clear database before shut down.
     */
    public @PreDestroy void flushTestDb() {
        factory.getConnection().flushDb();
    }

}
