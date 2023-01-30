package example.spring.data.orm.mybatis.model;

/**
 * @author liuzh_3nofxnp
 * @since 2016-01-22 22:16
 */
public class City extends BaseEntity {
    private String name;

    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
