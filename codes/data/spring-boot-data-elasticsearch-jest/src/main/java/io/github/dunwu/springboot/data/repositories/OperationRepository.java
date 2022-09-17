package io.github.dunwu.springboot.data.repositories;

import io.github.dunwu.springboot.data.entities.Operation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OperationRepository extends ElasticsearchRepository<Operation, Long> {
}
