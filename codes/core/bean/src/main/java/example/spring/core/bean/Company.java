package example.spring.core.bean;

import lombok.Data;

import java.util.List;

/**
 * Lombok 示例
 *
 * @author Zhang Peng
 * @see <a href= "http://jnb.ociweb.com/jnb/jnbJan2010.html">http://jnb.ociweb.com/jnb/jnbJan2010.html</a>
 */
@Data(staticConstructor = "of")
public class Company {

    private final Person founder;

    protected List<Person> employees;

    private String name;

}
