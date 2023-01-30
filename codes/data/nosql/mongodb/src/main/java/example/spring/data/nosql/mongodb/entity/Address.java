package example.spring.data.nosql.mongodb.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;

/**
 * A domain object to capture addresses.
 *
 * @author Oliver Gierke
 */
@Getter
@RequiredArgsConstructor
public class Address {

    private final Point location;
    private String street;
    private String zipCode;

}
