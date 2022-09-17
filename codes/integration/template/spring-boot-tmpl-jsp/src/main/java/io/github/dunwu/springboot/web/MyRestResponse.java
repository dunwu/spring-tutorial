package io.github.dunwu.springboot.web;

public class MyRestResponse {

    private String message;

    public MyRestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
