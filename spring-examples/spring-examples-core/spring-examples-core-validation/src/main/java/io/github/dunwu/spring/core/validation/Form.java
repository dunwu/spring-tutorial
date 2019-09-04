package io.github.dunwu.spring.core.validation;

public class Form {
    @DateString
    private String current;

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
