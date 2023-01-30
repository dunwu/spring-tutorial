package example.spring.core.bean;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest(classes = { BeanApplication.class })
public class BeanTests {

    @Autowired
    private BeanUtil beanUtil;

    @Test
    public void map() {
        User user = new User(1L, "userA", "用户A");
        UserDto userDto = beanUtil.map(user, UserDto.class);
        Assertions.assertThat(userDto.toString()).isEqualTo("UserDto(id=1, name=userA, infoDTO=InfoDto(content=用户A))");
        log.info("User 转化为 UserDto 的结果: {}", userDto);
    }

    @Test
    public void mapList() {
        User[] users = { new User(1L, "userA", "用户A"), new User(2L, "userB", "用户B"), new User(3L, "userC", "用户C"),
            new User(4L, "userD", "用户D") };
        List<UserDto> list = beanUtil.mapList(Arrays.asList(users), UserDto.class);
        Assertions.assertThat(list).hasSize(4);
        log.info("List<User> 转化为 List<UserDto> 的结果: {}", list);
    }

    @Test
    public void testCleanUp() {
        try {
            @Cleanup
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(new byte[] { 'Y', 'e', 's' });
            Assertions.assertThat(outputStream.toString()).isEqualTo("Yes");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testData() {
        Person huangshiren = new Person();
        huangshiren.setName("黄世仁");
        huangshiren.setAge(30);
        huangshiren.setSex("男");
        Person yangbailao = new Person();
        yangbailao.setName("杨白劳");
        yangbailao.setAge(50);
        yangbailao.setSex("男");
        Person xiaobaicai = new Person();
        xiaobaicai.setName("小白菜");
        xiaobaicai.setAge(20);
        xiaobaicai.setSex("女");

        List<Person> personList = new ArrayList<>();
        personList.add(yangbailao);
        personList.add(xiaobaicai);

        Company company = Company.of(huangshiren);
        company.setName("黑心农产品公司");
        company.setEmployees(personList);

        System.out.println("公司名：" + company.getName());
        System.out.println("创始人：" + company.getFounder());
        System.out.println("员工信息");
        company.getEmployees().forEach(person -> {
            log.info(person.toString());
        });
    }

    @Test
    public void testEqualsAndHashCode() {
        Person person = new Person();
        person.setName("张三");
        person.setAge(20);
        person.setSex("男");

        Person person2 = new Person();
        person2.setName("张三");
        person2.setAge(18);
        person2.setSex("男");

        Person person3 = new Person();
        person3.setName("李四");
        person3.setAge(20);
        person3.setSex("男");

        log.info("person: {}", person);
        log.info("person2: {}", person2);
        log.info("person3: {}", person3);
        Assertions.assertThat(person2).isEqualTo(person);
        Assertions.assertThat(person3).isNotEqualTo(person);
    }

    @Test
    public void testToString() {
        Person person = new Person();
        person.setName("张三");
        person.setAge(20);
        person.setSex("男");
        log.info("person: {}", person);
        Assertions.assertThat(person.toString()).isEqualTo("Person(name=张三, sex=男)");

        Person person2 = new Person("李四", 19, "男");
        log.info("person2: {}", person2);
        Assertions.assertThat(person2.toString()).isEqualTo("Person(name=李四, sex=男)");
    }

}
