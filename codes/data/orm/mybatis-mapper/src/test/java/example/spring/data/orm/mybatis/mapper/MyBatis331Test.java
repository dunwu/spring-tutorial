/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package example.spring.data.orm.mybatis.mapper;

import example.spring.data.orm.mybatis.MybatisMapperApplication;
import example.spring.data.orm.mybatis.model.City2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzh
 * @since 2016-03-06 17:42
 */
@Slf4j
@WebAppConfiguration
@Transactional
@SpringBootTest(classes = MybatisMapperApplication.class)
public class MyBatis331Test {

    @Autowired
    private MyBatis331Mapper mapper;

    @Test
    @Rollback
    public void testInsertList() {
        List<City2> city2List = new ArrayList<City2>();
        city2List.add(new City2("石家庄", "河北"));
        city2List.add(new City2("邯郸", "河北"));
        city2List.add(new City2("秦皇岛", "河北"));
        Assertions.assertEquals(3, mapper.insertCities(city2List));
        for (City2 c2 : city2List) {
            log.info(c2.toString());
            Assertions.assertNotNull(c2.getId());
        }
    }

    @Test
    public void testSelectById() {
        City2 city2 = mapper.selectByCityId(1);
        log.info(city2.toString());
        Assertions.assertNotNull(city2);
        Assertions.assertNotNull(city2.getCityName());
        Assertions.assertNotNull(city2.getCityState());
    }

    @Test
    public void testSelectAll() {
        List<City2> city2List = mapper.selectAll();
        for (City2 c2 : city2List) {
            log.info(c2.toString());
            Assertions.assertNotNull(c2);
            Assertions.assertNotNull(c2.getCityName());
            Assertions.assertNotNull(c2.getCityState());
        }
    }

}
