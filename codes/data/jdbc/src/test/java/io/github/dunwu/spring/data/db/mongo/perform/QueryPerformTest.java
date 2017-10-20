package io.github.dunwu.spring.data.db.mongo.perform;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zp on 16/9/7.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
        Assert.assertNotNull(iterable);
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
        Assert.assertNotNull(iterable);
    }


}
