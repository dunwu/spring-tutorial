package example.spring.trace.sleuth.entity;

public class Greeting {

    private String content;

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

}
