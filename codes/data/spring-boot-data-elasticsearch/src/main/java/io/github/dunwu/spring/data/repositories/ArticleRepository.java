package io.github.dunwu.spring.data.repositories;

import io.github.dunwu.spring.data.entities.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
}
