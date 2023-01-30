package example.spring.data.nosql.mongo;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static java.util.Arrays.asList;

public class InsertPrimer extends PrimerTestCase {

    @Test
    public void insertADocument() throws ParseException {

        // @begin: insert-a-document
        // @code: start
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        db.getCollection("restaurants")
          .insertOne(
              new Document("address",
                  new Document().append("street", "2 Avenue").append("zipcode", "10075")
                                .append("building", "1480").append("coord", asList(-73.9557413, 40.7720266)))
                  .append("borough", "Manhattan").append(
                      "cuisine", "Italian")
                  .append("grades", asList(new Document()
                          .append("date", format.parse("2014-10-01T00:00:00Z"))
                          .append("grade", "A").append("score", 11),
                      new Document()
                          .append("date", format.parse("2014-01-16T00:00:00Z"))
                          .append("grade", "B").append("score", 17)))
                  .append("name", "Vella").append("restaurant_id", "41704620"));
        // @code: end

        // @post: The method does not return a result.
        // @end: insert-a-document
    }

}
