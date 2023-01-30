package example.spring.data.nosql.mongo;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;

public class QueryPrimer extends PrimerTestCase {

    @Test
    public void queryAll() {
        // @begin: query-all
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants").find();
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        });
        // @code: end
        // @end: query-all
    }

    @Test
    public void logicalAnd() {

        // @begin: logical-and
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants")
                                            .find(
                                                new Document("cuisine", "Italian").append("address.zipcode", "10075"));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        // @code: end

        // @pre: To simplify building queries the Java driver provides static helpers
        // @code: start
        db.getCollection("restaurants").find(and(eq("cuisine", "Italian"), eq("address.zipcode", "10075")));
        // @code: end

        // @end: logical-and
    }

    @Test
    public void logicalOr() {

        // @begin: logical-or
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants").find(new Document("$or",
            asList(new Document("cuisine", "Italian"), new Document("address.zipcode", "10075"))));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        // @code: end

        // @pre: To simplify building queries the Java driver provides static helpers
        // @code: start
        db.getCollection("restaurants").find(or(eq("cuisine", "Italian"), eq("address.zipcode", "10075")));
        // @code: end

        // @end: logical-or
    }

    @Test
    public void queryTopLevelField() {
        // @begin: query-top-level-field
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants").find(new Document("borough", "Manhattan"));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        // @code: end

        // @pre: To simplify building queries the Java driver provides static helpers
        // @code: start
        db.getCollection("restaurants").find(eq("borough", "Manhattan"));
        // @code: end
        // @end: query-top-level-field
    }

    @Test
    public void queryEmbeddedDocument() {
        // @begin: query-embedded-document
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants")
                                            .find(new Document("address.zipcode", "10075"));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        // @code: end

        // @pre: To simplify building queries the Java driver provides static helpers
        // @code: start
        db.getCollection("restaurants").find(eq("address.zipcode", "10075"));
        // @code: end
        // @end: query-embedded-document
    }

    @Test
    public void queryFieldInArray() {
        // @begin: query-field-in-array
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants").find(new Document("grades.grade", "B"));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        // @code: end

        // @pre: To simplify building queries the Java driver provides static helpers
        // @code: start
        db.getCollection("restaurants").find(eq("grades.grade", "B"));
        // @code: end
        // @end: query-field-in-array
    }

    @Test
    public void greaterThan() {
        // @begin: greater-than
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants")
                                            .find(new Document("grades.score", new Document("$gt", 30)));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        // @code: end

        // @pre: To simplify building queries the Java driver provides static helpers
        // @code: start
        db.getCollection("restaurants").find(gt("grades.score", 30));
        // @code: end
        // @end: greater-than
    }

    @Test
    public void lessThan() {
        // @begin: less-than
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants")
                                            .find(new Document("grades.score", new Document("$lt", 10)));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document.
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        // @code: end

        // @pre: To simplify building queries the Java driver provides static helpers
        // @code: start
        db.getCollection("restaurants").find(lt("grades.score", 10));
        // @code: end
        // @end: less-than
    }

    @Test
    public void sort() {
        // @begin: sort
        // @code: start
        FindIterable<Document> iterable = db.getCollection("restaurants").find()
                                            .sort(new Document("borough", 1).append("address.zipcode", 1));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        // @code: end

        // @pre: To simplify sorting fields the Java driver provides static helpers
        // @code: start
        db.getCollection("restaurants").find().sort(ascending("borough", "address.zipcode"));
        // @code: end
        // @end: sort
    }

}
