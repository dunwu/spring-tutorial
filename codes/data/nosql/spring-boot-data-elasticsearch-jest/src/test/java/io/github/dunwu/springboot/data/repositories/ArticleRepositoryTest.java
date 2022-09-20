package io.github.dunwu.springboot.data.repositories;

import io.github.dunwu.springboot.SpringBootDataElasticsearchApplication;
import io.github.dunwu.springboot.data.entities.Article;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataElasticsearchApplication.class })
public class ArticleRepositoryTest {

    @Resource
    private ArticleRepository repository;

    @Before
    public void clear() {
        repository.deleteAll();
    }

    @Test
    public void shouldIndexSingleBookEntity() {

        Article article = new Article();
        article.setId("123455");
        article.setTitle("Spring Data Elasticsearch Test Article");
        List<String> authors = new ArrayList<>();
        authors.add("Author1");
        authors.add("Author2");
        article.setAuthors(authors);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");
        article.setTags(tags);

        // save document
        repository.save(article);

        // find document
        Article result = repository.findById(article.getId()).orElse(null);
        Assert.assertNotNull(result);
        Assert.assertEquals(article.getId(), result.getId());
        Assert.assertEquals(authors.size(), result.getAuthors().size());
        Assert.assertEquals(tags.size(), result.getTags().size());
    }

}
