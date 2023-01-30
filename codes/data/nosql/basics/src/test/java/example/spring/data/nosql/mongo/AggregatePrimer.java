package example.spring.data.nosql.mongo;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;

public class AggregatePrimer extends PrimerTestCase {

    @Test
    public void groupDocumentsByAFieldAndCalculateCount() {

        // @begin: group-documents-by-a-field-and-calculate-count
        // @code: start
        AggregateIterable<Document> iterable = db.getCollection("restaurants").aggregate(asList(
            new Document("$group", new Document("_id", "$borough").append("count", new Document("$sum", 1)))));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        });
        // @code: end

        /*
         * // @results: start { "_id" : "Missing", "count" : 51 } { "_id" :
         * "Staten Island", "count" : 969 } { "_id" : "Manhattan", "count" : 10259 } {
         * "_id" : "Brooklyn", "count" : 6086 } { "_id" : "Queens", "count" : 5656 } {
         * "_id" : "Bronx", "count" : 2338 } // @results: end
         */

        // @end: group-documents-by-a-field-and-calculate-count
    }

    @Test
    public void filterAndGroupDocuments() {

        // @begin: filter-and-group-documents
        // @code: start
        AggregateIterable<Document> iterable = db.getCollection("restaurants")
                                                 .aggregate(asList(
                                                     new Document("$match",
                                                         new Document("borough", "Queens").append("cuisine",
                                                             "Brazilian")),
                                                     new Document("$group",
                                                         new Document("_id", "$address.zipcode").append("count",
                                                             new Document("$sum", 1)))));
        // @code: end

        // @pre: Iterate the results and apply a block to each resulting document
        // @code: start
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        });
        // @code: end

        /*
         * // @results: start { "_id" : "11377", "count" : 1 } { "_id" : "11368", "count"
         * : 1 } { "_id" : "11101", "count" : 2 } { "_id" : "11106", "count" : 3 } { "_id"
         * : "11103", "count" : 1 } // @results: end
         */

        // @end: filter-and-group-documents
    }

}
