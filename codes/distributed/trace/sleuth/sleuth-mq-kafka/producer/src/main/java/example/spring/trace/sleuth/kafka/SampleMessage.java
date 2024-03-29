package example.spring.trace.sleuth.kafka;

/**
 * 示例 01 的 Message 消息
 */
public class SampleMessage {

    /**
     * 编号
     */
    private Integer id;

    public SampleMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo01Message{" +
                "id=" + id +
                '}';
    }

}
