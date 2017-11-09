package io.github.dunwu.spring.libs.dozer.vo;

import org.dozer.Mapping;

public class SourceBean {

    private Long id;

    private String name;

    @Mapping("binaryData")
    private String data;

    @Mapping("pk")
    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
