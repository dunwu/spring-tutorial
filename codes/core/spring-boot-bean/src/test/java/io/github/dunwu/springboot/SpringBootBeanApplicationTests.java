package io.github.dunwu.springboot;

import io.github.dunwu.springboot.bean.Company;
import io.github.dunwu.springboot.bean.Person;
import io.github.dunwu.springboot.bean.UserDO;
import io.github.dunwu.springboot.bean.UserDTO;
import lombok.Cleanup;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootBeanApplication.class })
public class SpringBootBeanApplicationTests {

    private final Logger log = LoggerFactory.getLogger(SpringBootBeanApplicationTests.class);

    @Rule
    public OutputCaptureRule outputCapture = new OutputCaptureRule();

    @Autowired
    private BeanUtil beanUtil;

    @Test
    public void map() {
        UserDO userDO = new UserDO(1L, "userA", "用户A");
        UserDTO userDTO = beanUtil.map(userDO, UserDTO.class);
        assertThat(userDTO.toString()).isEqualTo("UserDTO(id=1, name=userA, infoDTO=InfoDTO(content=用户A))");
        log.info("UserDO 转化为 UserDTO 的结果: {}", userDTO.toString());
    }

    @Test
    public void mapList() {
        UserDO[] userDOS = { new UserDO(1L, "userA", "用户A"), new UserDO(2L, "userB", "用户B"),
            new UserDO(3L, "userC", "用户C"), new UserDO(4L, "userD", "用户D") };
        List<UserDTO> list = beanUtil.mapList(Arrays.asList(userDOS), UserDTO.class);
        assertThat(list).hasSize(4);
        log.info("List<UserDO> 转化为 List<UserDTO> 的结果: {}", list);
    }

    @Test
    public void testCleanUp() {
        try {
            @Cleanup
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(new byte[] { 'Y', 'e', 's' });
            assertThat(baos.toString()).isEqualTo("Yes");
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
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
        assertThat(person2).isEqualTo(person);
        assertThat(person3).isNotEqualTo(person);
    }

    @Test
    public void testToString() {
        Person person = new Person();
        person.setName("张三");
        person.setAge(20);
        person.setSex("男");
        log.info("person: {}", person);
        assertThat(person.toString()).isEqualTo("Person(name=张三, sex=男)");

        Person person2 = new Person("李四", 19, "男");
        log.info("person2: {}", person2);
        assertThat(person2.toString()).isEqualTo("Person(name=李四, sex=男)");
    }

}
