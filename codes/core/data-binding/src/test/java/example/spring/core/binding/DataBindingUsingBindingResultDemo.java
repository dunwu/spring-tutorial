package example.spring.core.binding;

import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-11
 */
public class DataBindingUsingBindingResultDemo {

    @Test
    public void test() {
        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("num", "10x"); // 10x cannot be converted to int type

        TestBean testBean = new TestBean();
        DataBinder db = new DataBinder(testBean);

        db.bind(mpv);
        db.getBindingResult().getAllErrors().forEach(System.out::println);
        System.out.println(testBean);
    }

}
