package io.github.dunwu.spring.data.db.mongo.perform;

import org.bson.Document;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by zp on 16/9/7.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsertPerform extends MongoDBConnector {
    final static int MAX = 10000;
    static List<Document> list = new ArrayList<Document>();
    final static Logger log = LoggerFactory.getLogger(InsertPerform.class);

    /**
     * 初始化环境
     */
    static {
        long start = System.currentTimeMillis();
        // 连接到数据库
        // db.getCollection("tmp_collection").drop();

        // 构造测试数据
        for (int i = 0; i < MAX; i++) {
            list.add(getRandomDocument());
        }

        long end = System.currentTimeMillis() - start;
        log.debug("init耗时：" + end);
    }

    private static Document getRandomDocument() {
        String uuid24 = UUID.randomUUID().toString().replace("-", "").substring(0, 23);
        Random random = new Random(System.currentTimeMillis());

        Document doc = new Document("_id", new Document("oid", uuid24));
        doc.append("itemid", getUUID(32));
        doc.append("domain", "saksfifthavenue");
        List<Float> list = new ArrayList<Float>();

        for (int i = 0; i < 4096; i++) {
            list.add(random.nextFloat());
        }
        doc.append("fc6", list);
        doc.append("height", random.nextInt(600));
        doc.append("width", random.nextInt(600));
        doc.append("location", "127.0.0.1");
        doc.append("pic_type", "data");
        doc.append("store", 6);
        return doc;
    }

    private static String getUUID(int length) {
        if (length > 32) return null;
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    @Test
    public void test01_insertMany() {
        db.getCollection("tmp_collection").insertMany(list);
    }

    @Test
    public void test02_insertOne() {
        db.getCollection("tmp_collection").insertOne(getRandomDocument());
    }

}
