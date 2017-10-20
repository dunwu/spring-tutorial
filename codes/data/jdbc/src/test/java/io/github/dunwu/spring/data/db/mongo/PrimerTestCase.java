package io.github.dunwu.spring.data.db.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Created by zp on 16/9/6.
 */
public class PrimerTestCase {
    static MongoDatabase db = null;
    private static MongoClient mongoClient;

    @BeforeClass
    public static void init() {
        mongoClient = new MongoClient("localhost", 27017);

        // 连接到数据库
        db = mongoClient.getDatabase("test");
    }

    @AfterClass
    public static void close() {
        if (null != mongoClient) {
            mongoClient.close();
        }
    }
}
