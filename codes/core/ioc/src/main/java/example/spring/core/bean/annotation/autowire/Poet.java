package example.spring.core.bean.annotation.autowire;

/**
 * 诗人
 */
public class Poet implements Performer {

    private String name;

    private Poetry poetry;

    public Poet() {
    }

    public Poet(String name, Poetry poetry) {
        this.name = name;
        this.poetry = poetry;
    }

    @Override
    public void perform() throws Exception {
        System.out.printf("%s朗诵诗歌：%s%n", name, poetry.getName());
    }

}
