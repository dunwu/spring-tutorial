package io.github.dunwu.spring.libs.dozer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.dunwu.spring.libs.dozer.vo.Dest;
import io.github.dunwu.spring.libs.dozer.vo.NotSameAttributeA;
import io.github.dunwu.spring.libs.dozer.vo.NotSameAttributeB;
import io.github.dunwu.spring.libs.dozer.vo.SameAttributeA;
import io.github.dunwu.spring.libs.dozer.vo.SameAttributeB;
import io.github.dunwu.spring.libs.dozer.vo.Source;
import io.github.dunwu.spring.libs.dozer.vo.SourceBean;
import io.github.dunwu.spring.libs.dozer.vo.Status;
import io.github.dunwu.spring.libs.dozer.vo.TargetBean;
import io.github.dunwu.spring.libs.dozer.vo.User;
import io.github.dunwu.spring.libs.dozer.vo.UserGroup;
import io.github.dunwu.spring.libs.dozer.vo.UserGroupPrime;
import io.github.dunwu.spring.libs.dozer.vo.UserPrime;
import junit.framework.TestCase;

/**
 * @author Zhang Peng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dozer.xml" })
public class CustomMappingTest extends TestCase {
    @Autowired
    Mapper mapper;

    Logger log = LoggerFactory.getLogger(CustomMappingTest.class);

    @Test
    public void testSameAttributeMapping() {
        SameAttributeA src = new SameAttributeA();
        src.setId(007);
        src.setName("邦德");
        src.setDate(new Date());

        SameAttributeB desc = mapper.map(src, SameAttributeB.class);
        Assert.assertNotNull(desc);
        log.debug("Source -> Destination success. id={}, name={}, date={}", desc.getId(), desc.getName(),
                desc.getDate());
    }

    @Test
    public void testNotSameAttributeMapping() {
        NotSameAttributeA src = new NotSameAttributeA();
        src.setId(007);
        src.setName("邦德");
        src.setDate(new Date());

        NotSameAttributeB desc = mapper.map(src, NotSameAttributeB.class);
        Assert.assertNotNull(desc);
        log.debug("Source -> Destination success. id={}, value={}, date={}", desc.getId(), desc.getValue(),
                desc.getDate());
    }

    /**
     * 测试注解方式配置映射
     */
    @Test
    public void testAnnotationMapping() {
        SourceBean src = new SourceBean();
        src.setId(7L);
        src.setName("邦德");
        src.setData("00000111");

        TargetBean desc = mapper.map(src, TargetBean.class);
        Assert.assertNotNull(desc);
        log.debug("Source -> Destination success. pk={}, name={}, data={}", desc.getPk(), desc.getName(),
                desc.getBinaryData());
    }

    /**
     * 测试枚举映射
     */
    @Test
    public void testEnumMapping() {
        User src = new User();
        src.setStatus(Status.SUCCESS);
        UserPrime desc = mapper.map(src, UserPrime.class);

        Assert.assertNotNull(desc);
        log.debug("Source -> Destination success. status={}", desc.getStatusPrime());
    }

    /**
     * Dozer会自动转换集合 本例中set -> list
     */
    @Test
    public void testCollectionToCollection() {
        Set<User> sets = new HashSet<User>();

        User src1 = new User();
        src1.setStatus(Status.SUCCESS);
        sets.add(src1);

        User src2 = new User();
        src2.setStatus(Status.ERROR);
        sets.add(src2);

        User src3 = new User();
        src3.setStatus(Status.PROCESSING);
        sets.add(src3);

        UserGroup src = new UserGroup();
        src.setUsers(sets);

        UserGroupPrime desc = mapper.map(src, UserGroupPrime.class);

        Assert.assertNotNull(desc);
        for (UserPrime item : desc.getUsers()) {
            log.debug("Source -> Destination success. status={}", item.getStatusPrime());
        }

    }

    /**
     *
     */
    @Test
    public void testDeepMapping() {
        Source src = new Source();
        src.setId(1000L);
        src.setInfo("深度映射");
        Dest dest = mapper.map(src, Dest.class);

        Assert.assertNotNull(dest);
        log.debug("Source -> Destination success. id={}, info={}", dest.getId(), dest.getInfo().getContent());
    }
}
