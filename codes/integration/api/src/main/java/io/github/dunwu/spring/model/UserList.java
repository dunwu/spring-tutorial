package io.github.dunwu.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

/**
 * @author Zhang Peng
 */
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.NONE)
@ApiModel(value = "用户列表", description = "用户列表")
public class UserList implements Serializable {

    @XmlElement(name = "user")
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
