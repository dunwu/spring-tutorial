package org.zp.notes.spring.common.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.Date;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) {
        Info info = new Info();
        info.setId(100);
        info.setCreateDate(new Date());

        User user = new User();
        user.setName("zhangsan");
        user.setId(100);
        user.setPassword("123456");
        user.setInfo(info);


        Mapper mapper = new DozerBeanMapper();
        UserVO userVO = mapper.map(user, UserVO.class);
        System.out.println("Hello World!");
    }
}
