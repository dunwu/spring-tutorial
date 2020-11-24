package io.github.dunwu.spring.data.repositories;

import io.github.dunwu.spring.data.entities.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<User, String> {

    List<User> findByUsername(String username);

    User findByEmail(String email);

}
