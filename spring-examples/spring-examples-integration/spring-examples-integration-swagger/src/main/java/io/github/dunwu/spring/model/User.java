package io.github.dunwu.spring.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Zhang Peng
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
@ApiModel(value = "用户信息", description = "用户信息")
public class User implements Serializable {

    @XmlElement
    @ApiModelProperty(value = "用户编号", dataType = "Long", required = true)
    private Long id;

    @XmlElement
    @ApiModelProperty(value = "姓名", dataType = "String", required = false)
    private String name;

    @XmlElement
    @ApiModelProperty(value = "电子邮件", dataType = "String", required = false)
    private String email;

    public User() {
    }

    public User(Long id, String name, String email) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
