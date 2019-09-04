/**
 * The Apache License 2.0
 * Copyright (c) 2016 Zhang Peng
 */
package io.github.dunwu.spring.data.db.redis.bean;

import java.io.Serializable;

/**
 * @author Zhang Peng
 * @date 2017/4/12.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String password;

    public UserDTO() {

    }

    public UserDTO(String id, String name, String password) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
