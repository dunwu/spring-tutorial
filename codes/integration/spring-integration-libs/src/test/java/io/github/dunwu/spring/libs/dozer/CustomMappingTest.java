package io.github.dunwu.spring.libs.dozer;

import com.github.dozermapper.core.Mapper;
import io.github.dunwu.spring.libs.dozer.vo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Zhang Peng
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dozer.xml" })
public class CustomMappingTest {

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
        Assertions.assertNotNull(desc);
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
        Assertions.assertNotNull(desc);
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
        Assertions.assertNotNull(desc);
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

        Assertions.assertNotNull(desc);
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

        Assertions.assertNotNull(desc);
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

        Assertions.assertNotNull(dest);
        log.debug("Source -> Destination success. id={}, info={}", dest.getId(), dest.getInfo().getContent());
    }

}
