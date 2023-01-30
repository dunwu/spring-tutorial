package example.spring.data.nosql.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by zp on 16/9/6.
 */
@ExtendWith(SpringExtension.class)
public class PrimerTestCase {

    static MongoDatabase db = null;

    private static MongoClient mongoClient;

    @BeforeAll
    public static void init() {
        mongoClient = new MongoClient("localhost", 27017);

        // 连接到数据库
        db = mongoClient.getDatabase("test");
    }

    @AfterAll
    public static void close() {
        if (null != mongoClient) {
            mongoClient.close();
        }
    }

}
