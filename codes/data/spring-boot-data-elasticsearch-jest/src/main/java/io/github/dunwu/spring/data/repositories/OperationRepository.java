package io.github.dunwu.spring.data.repositories;

import io.github.dunwu.spring.data.entities.Operation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OperationRepository extends ElasticsearchRepository<Operation, Long> {
}
