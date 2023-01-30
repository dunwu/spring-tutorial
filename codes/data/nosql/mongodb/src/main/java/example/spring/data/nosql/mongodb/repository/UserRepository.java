package example.spring.data.nosql.mongodb.repository;

import example.spring.data.nosql.mongodb.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色信息 MongoDB Repository
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-29
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findUserByUserId(Long userId);

    List<User> findUsersByUsername(String username);

    List<User> findPagedUsersByUsernameLike(String username, PageRequest pageRequest);

}
