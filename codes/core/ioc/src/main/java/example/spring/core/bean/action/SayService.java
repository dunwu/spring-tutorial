package example.spring.core.bean.action;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * SayService 是有状态，如果 SayService 是单例的话必然会 OOM
 */
@Slf4j
public abstract class SayService {

    List<String> data = new ArrayList<>();

    public void say() {
        data.add(IntStream.rangeClosed(1, 1000000)
                          .mapToObj(__ -> "a")
                          .collect(Collectors.joining("")) + UUID.randomUUID().toString());
        log.info("I'm {} size:{}", this, data.size());
    }

}
