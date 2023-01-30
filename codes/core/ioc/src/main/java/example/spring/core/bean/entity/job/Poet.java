package example.spring.core.bean.entity.job;

import lombok.Data;

/**
 * 诗人
 */
@Data
public class Poet implements Performer {

    private String name;

    private Poetry poetry;

    public Poet() { }

    public Poet(String name, Poetry poetry) {
        this.name = name;
        this.poetry = poetry;
    }

    @Override
    public void perform() {
        System.out.printf("%s朗诵诗歌： %s%n", name, poetry.getName());
    }

}
