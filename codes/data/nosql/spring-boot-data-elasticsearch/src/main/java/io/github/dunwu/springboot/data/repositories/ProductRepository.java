package io.github.dunwu.springboot.data.repositories;

import io.github.dunwu.springboot.data.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    List<Product> findByName(String name);

    List<Product> findByName(String name, Pageable pageable);

    List<Product> findByNameAndId(String name, String id);

}
