package io.github.dunwu.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Zhang Peng
 * @since 2018-11-02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {

    private Long id;

    private String name;

    private String info;

}
