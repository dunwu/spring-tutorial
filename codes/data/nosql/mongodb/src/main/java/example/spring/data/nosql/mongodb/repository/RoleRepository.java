package example.spring.data.nosql.mongodb.repository;

import example.spring.data.nosql.mongodb.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色信息 MongoDB Repository
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-29
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, String> {
}
