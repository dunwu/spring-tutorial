package example.spring.data.nosql.mongo.perform;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * Created by zp on 16/9/6.
 */
public class MongoDBConnector {

    public static MongoDatabase db = null;

    public static MongoClient mongoClient = null;

    @BeforeAll
    public static void beforeClass() {
        // 连接到数据库
        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDatabase("test");
    }

    @AfterAll
    public static void afterClass() {
        if (null != mongoClient) {
            mongoClient.close();
        }
    }

}
