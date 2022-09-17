package io.github.dunwu.springboot;

import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeanUtil {

    @Autowired
    private Mapper dozerMapper;

    /**
     * JavaBean List 对象转换
     *
     * @param sourceList dmoList
     * @param targetClz  dtoClz
     * @param <S>        源clz
     * @param <T>        目标clz
     * @return 转化后的list
     */
    public <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClz) {
        List<T> targetList = new ArrayList<>();
        if (null == sourceList || sourceList.isEmpty()) {
            return targetList;
        }

        targetList = sourceList.stream().map(item -> (map(item, targetClz))).collect(Collectors.toList());

        return targetList;
    }

    /**
     * JavaBean 对象转换
     *
     * @param sourceObj 源对象
     * @param targetClz 目标对象
     * @param <S>       源对象clz
     * @param <T>       目标对象clz
     * @return 目标对象
     */
    public <S, T> T map(S sourceObj, Class<T> targetClz) {
        if (null == sourceObj) {
            return null;
        }

        return dozerMapper.map(sourceObj, targetClz);
    }

}
