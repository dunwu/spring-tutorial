package io.github.dunwu.springboot.data.repositories;

import io.github.dunwu.springboot.data.entities.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<User, String> {

    List<User> findByUserName(String UserName);

    User findByEmail(String email);

}
