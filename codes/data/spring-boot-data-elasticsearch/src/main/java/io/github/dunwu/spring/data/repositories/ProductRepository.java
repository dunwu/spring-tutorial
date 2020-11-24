package io.github.dunwu.spring.data.repositories;

import io.github.dunwu.spring.data.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    List<Product> findByName(String name);

    List<Product> findByName(String name, Pageable pageable);

    List<Product> findByNameAndId(String name, String id);

}
