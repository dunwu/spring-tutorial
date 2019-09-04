package io.github.dunwu.spring.data.db.mongo;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zp on 16/9/6.
 */
public class MongoDBTest {
    final static Logger log = LoggerFactory.getLogger(MongoDBTest.class);
    static MongoDatabase db;
    static {
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // 连接到数据库
        db = mongoClient.getDatabase("test");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        logicalAnd();
        long time = System.currentTimeMillis() - start;
        log.info("耗时：{}", time);
    }

    public static void logicalAnd() {

        // @begin: logical-and
        // @code: start
        // FindIterable<Document> iterable = db.getCollection("box_vgg_fea").find(new
        // Document("domain", "shopbop")).limit(10);
        FindIterable<Document> iterable = db.getCollection("box_vgg_fea").find().limit(10);
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                log.info(document.toJson());
            }
        });
        // @code: end

        // @pre: To simplify building queries the Java driver provides static helpers
        // @code: start
        // db.getCollection("restaurants").find(and(eq("cuisine", "Italian"), eq("address.zipcode",
        // "10075")));
        // @code: end

        // @end: logical-and
    }
}
