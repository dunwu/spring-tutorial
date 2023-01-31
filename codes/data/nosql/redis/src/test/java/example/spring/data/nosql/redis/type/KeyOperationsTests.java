package example.spring.data.nosql.redis.type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Show usage of operations on redis keys using low level API provided by {@link RedisConnection}.
 *
 * @author Christoph Strobl
 */
@SpringBootTest
public class KeyOperationsTests {

    private static final String PREFIX = KeyOperationsTests.class.getSimpleName();
    private static final String KEY_PATTERN = PREFIX + "*";

    @Autowired
    RedisConnectionFactory connectionFactory;

    private RedisConnection connection;
    private RedisSerializer<String> serializer = new StringRedisSerializer();

    @BeforeEach
    public void setUp() {
        this.connection = connectionFactory.getConnection();
    }

    /**
     * Uses {@code KEYS} command for loading all matching keys. <br /> Note that {@code KEYS} is a blocking command that
     * potentially might affect other operations execution time. <br /> All keys will be loaded within <strong>one
     * single</strong> operation.
     */
    @Test
    public void iterateOverKeysMatchingPrefixUsingKeysCommand() {

        generateRandomKeys(1000);

        Set<byte[]> keys = this.connection.keys(serializer.serialize(KEY_PATTERN));
        printKeys(keys.iterator());
    }

    /**
     * Uses {@code SCAN} command for loading all matching keys. <br /> {@code SCAN} uses a cursor on server side
     * returning only a subset of the available data with the possibility to ripple load further elements using the
     * cursors position. <br /> All keys will be loaded using <strong>multiple</strong> operations.
     */
    @Test
    public void iterateOverKeysMatchingPrefixUsingScanCommand() {

        generateRandomKeys(1000);

        Cursor<byte[]> cursor = this.connection.scan(ScanOptions.scanOptions().match(KEY_PATTERN).build());
        printKeys(cursor);
    }

    private void printKeys(Iterator<byte[]> keys) {

        int i = 0;
        while (keys.hasNext()) {
            System.out.println(new String(keys.next()));
            i++;
        }
        System.out.println(String.format("Total No. found: %s", i));
    }

    private void generateRandomKeys(int nrKeys) {

        for (int i = 0; i < nrKeys; i++) {
            this.connection.set((PREFIX + "-" + i).getBytes(), UUID.randomUUID().toString().getBytes());
        }
    }

}
