package example.spring.data.orm.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("all")
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubUserService subUserService;

    /**
     * 写DB并触发异常，但不会触发事务回滚
     */
    @Transactional
    public void fail1(User entity) {
        try {
            userRepository.save(entity);
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
        }
    }

    /**
     * 写DB并触发异常，但不会触发事务回滚
     */
    @Transactional
    public void fail2(User entity) throws IOException {
        userRepository.save(entity);
        otherTask();
    }

    private void otherTask() throws IOException {
        Files.readAllLines(Paths.get("file-that-not-exist"));
    }

    /**
     * 间接调用公有方法写DB并触发异常，但不会触发事务回滚
     */
    public void fail3(User entity) {
        try {
            this.createUserPublic(entity);
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
    }

    /**
     * 间接调用私有方法写DB并触发异常，但不会触发事务回滚
     */
    public void fail4(User entity) {
        try {
            this.createUserPrivate(entity);
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
    }

    @Transactional
    public void createUserPublic(User entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test")) { throw new RuntimeException("invalid username!"); }
    }

    @Transactional
    void createUserPrivate(User entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test")) { throw new RuntimeException("invalid username!"); }
    }

    /**
     * 写DB并触发异常，会触发事务回滚
     */
    @Transactional
    public void ok1(User entity) {
        try {
            userRepository.save(entity);
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed", ex);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        log.info("result {} ", userRepository.findByName(entity.getName()));
        log.info("result {} ", userRepository.findByEmail(entity.getEmail()));
    }

    /**
     * 写DB并触发异常，会触发事务回滚
     */
    @Transactional(rollbackFor = Exception.class)
    public void ok2(User entity) throws IOException {
        userRepository.save(entity);
        otherTask();
    }

    @Transactional
    public void ok3(User entity) {
        createMainUser(entity);
        subUserService.createSubUserWithExceptionWrong(entity);
    }

    @Transactional
    public void ok4(User entity) {
        createMainUser(entity);
        try {
            subUserService.createSubUserWithExceptionWrong(entity);
        } catch (Exception ex) {
            // 虽然捕获了异常，但是因为没有开启新事务，而当前事务因为异常已经被标记为rollback了，所以最终还是会回滚。
            log.error("create sub user error:{}", ex.getMessage());
        }
    }

    @Transactional
    public void fail5(User entity) {
        createMainUser(entity);
        try {
            subUserService.createSubUserWithExceptionRight(entity);
        } catch (Exception ex) {
            // 捕获异常，防止主方法回滚
            log.error("create sub user error:{}", ex.getMessage());
        }
    }

    private void createMainUser(User entity) {
        userRepository.save(entity);
        log.info("createMainUser finish");
    }

}
