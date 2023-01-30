package example.spring.data.nosql.mongo;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;

public class UpdatePrimer extends PrimerTestCase {

    @Test
    public void updateTopLevelFields() {

        // @begin: update-top-level-fields
        // @code: start
        db.getCollection("restaurants").updateOne(new Document("name", "Juni"),
            new Document("$set", new Document("cuisine", "American (New)")).append("$currentDate",
                new Document("lastModified", true)));
        // @code: end

        /*
         * // @post: start The updateOne operation returns a ``UpdateResult`` which
         * contains information about the operation. The ``getModifiedCount`` method
         * returns the number of documents modified. // @post: end
         */
        // @end: update-top-level-fields
    }

    @Test
    public void updateEmbeddedField() {
        // @begin: update-embedded-field
        // @code: start
        db.getCollection("restaurants").updateOne(new Document("restaurant_id", "41156888"),
            new Document("$set", new Document("address.street", "East 31st Street")));

        // @code: end
        /*
         * // @post: start The updateOne operation returns a ``UpdateResult`` which
         * contains information about the operation. The ``getModifiedCount`` method
         * returns the number of documents modified. // @post: end
         */
        // @end: update-embedded-field
    }

    @Test
    public void updateMultipleDocuments() {

        // @begin: update-multiple-documents
        // @code: start
        db.getCollection("restaurants").updateMany(new Document("address.zipcode", "10016").append("cuisine", "Other"),
            new Document("$set", new Document("cuisine", "Category To Be Determined")).append("$currentDate",
                new Document("lastModified", true)));
        // @code: end

        /*
         * // @post: start The updateMany operation returns a ``UpdateResult`` which
         * contains information about the operation. The ``getModifiedCount`` method
         * returns the number of documents modified. // @post: end
         */
        // @end: update-multiple-documents
    }

    @Test
    public void replaceDocument() {

        // @begin: replace-document
        // @code: start
        db.getCollection("restaurants").replaceOne(new Document("restaurant_id", "41704620"),
            new Document("address",
                new Document().append("street", "2 Avenue").append("zipcode", "10075")
                              .append("building", "1480").append("coord", asList(-73.9557413, 40.7720266)))
                .append("name", "Vella 2"));
        // @code: end
        /*
         * // @post: start The replaceOne operation returns a ``UpdateResult`` which
         * contains information about the operation. The ``getModifiedCount`` method
         * returns the number of documents modified. // @post: end
         */

        // @end: replace-document
    }

}
