package example.spring.data.nosql.mongo;

import org.bson.Document;
import org.junit.jupiter.api.Test;

public class IndexesPrimer extends PrimerTestCase {

    @Test
    public void singleFieldIndex() {

        // @begin: single-field-index
        // @code: start
        db.getCollection("restaurants").createIndex(new Document("cuisine", 1));
        // @code: end

        // @post: The method does not return a result.
        // @end: single-field-index
    }

    @Test
    public void createCompoundIndex() {
        // @begin: create-compound-index
        // @code: start
        db.getCollection("restaurants").createIndex(new Document("cuisine", 1).append("address.zipcode", -1));
        // @code: end

        // @post: The method does not return a result.
        // @end: create-compound-index
    }

}
