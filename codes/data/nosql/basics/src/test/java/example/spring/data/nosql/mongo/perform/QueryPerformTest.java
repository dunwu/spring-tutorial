package example.spring.data.nosql.mongo.perform;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by zp on 16/9/7.
 */
@ExtendWith(SpringExtension.class)
public class QueryPerformTest extends MongoDBConnector {

    final static Logger log = LoggerFactory.getLogger(QueryPerformTest.class);

    @Test
    public void test01_queryAll() {
        FindIterable<Document> iterable = db.getCollection("tmp_collection").find();
        log.debug("tmp_collection总文档数: {}", db.getCollection("tmp_collection").count());
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                log.debug(document.getString("itemid"));
            }
        });
        Assertions.assertThat(iterable).isNotNull();
    }

    @Test
    public void test03_queryTop() {
        FindIterable<Document> iterable = db.getCollection("tmp_collection").find().limit(5);
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                log.debug(document.toJson());
            }
        });
        Assertions.assertThat(iterable).isNotNull();
    }

}
