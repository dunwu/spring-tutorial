package example.spring.core.bean.entity.job;

import lombok.Data;

/**
 * 诗歌
 */
@Data
public class Poetry {

    String name;

    public Poetry(String name) {
        this.name = name;
    }

}
