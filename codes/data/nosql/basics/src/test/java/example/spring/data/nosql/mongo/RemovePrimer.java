package example.spring.data.nosql.mongo;

import org.bson.Document;
import org.junit.jupiter.api.Test;

public class RemovePrimer extends PrimerTestCase {

    @Test
    public void removeMatchingDocuments() {
        // @begin: remove-matching-documents
        // @code: start
        db.getCollection("restaurants").deleteMany(new Document("borough", "Manhattan"));
        // @code: end

        /*
         * // @post: start The deleteMany operation returns a ``DeleteResult`` which
         * contains information about the operation. The ``getDeletedCount`` method
         * returns number of documents deleted. // @post: end
         */
        // @end: remove-matching-documents
    }

    @Test
    public void removeAllDocuments() {
        // @begin: remove-all-documents
        // @code: start
        db.getCollection("restaurants").deleteMany(new Document());
        // @code: end

        /*
         * // @post: start The deleteMany operation returns a ``DeleteResult`` which
         * contains information about the operation. The ``getDeletedCount`` method
         * returns number of documents deleted. // @post: end
         */
        // @end: remove-all-documents
    }

    @Test
    public void dropCollection() {
        // @begin: drop-collection
        // @code: start
        db.getCollection("restaurants").drop();
        // @code: end
        // @end: drop-collection
    }

}
