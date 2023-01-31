package example.spring.data.nosql.redis.type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link GeoOperations} 操作示例
 * <p>
 * 注意：Redis 必须要在 3.2 版本以上，才支持 GEO 操作
 *
 * @author Mark Paluch
 */
@SpringBootTest
public class GeoOperationsTests {

    @Autowired
    private RedisOperations<String, String> operations;
    private GeoOperations<String, String> geoOperations;

    @BeforeEach
    public void before() {

        geoOperations = operations.opsForGeo();

        geoOperations.add("Sicily", new Point(13.361389, 38.115556), "Arigento");
        geoOperations.add("Sicily", new Point(15.087269, 37.502669), "Catania");
        geoOperations.add("Sicily", new Point(13.583333, 37.316667), "Palermo");
    }

    /**
     * Look up points using a geo-index member as reference.
     */
    @Test
    public void geoRadiusByMember() {

        GeoResults<GeoLocation<String>> byDistance = geoOperations.radius("Sicily", "Palermo",
            new Distance(100, DistanceUnit.KILOMETERS));

        assertThat(byDistance).hasSize(2).extracting("content.name").contains("Arigento", "Palermo");

        GeoResults<GeoLocation<String>> greaterDistance = geoOperations.radius("Sicily", "Palermo",
            new Distance(200, DistanceUnit.KILOMETERS));

        assertThat(greaterDistance).hasSize(3).extracting("content.name").contains("Arigento", "Catania", "Palermo");
    }

    /**
     * Lookup points within a circle around coordinates.
     */
    @Test
    public void geoRadius() {

        Circle circle = new Circle(new Point(13.583333, 37.316667), //
            new Distance(100, DistanceUnit.KILOMETERS));
        GeoResults<GeoLocation<String>> result = geoOperations.radius("Sicily", circle);

        assertThat(result).hasSize(2).extracting("content.name").contains("Arigento", "Palermo");
    }

    /**
     * Calculate the distance between two geo-index members.
     */
    @Test
    public void geoDistance() {

        Distance distance = geoOperations.distance("Sicily", "Catania", "Palermo", DistanceUnit.KILOMETERS);

        assertThat(distance.getValue()).isBetween(130d, 140d);
    }

    /**
     * Return the geo-hash.
     */
    @Test
    public void geoHash() {

        List<String> geohashes = geoOperations.hash("Sicily", "Catania", "Palermo");

        assertThat(geohashes).hasSize(2).contains("sqdtr74hyu0", "sq9sm1716e0");
    }

}
