package io.github.dunwu.springboot.data.proxyfailed;

import io.github.dunwu.springboot.data.UserEntity;
import io.github.dunwu.springboot.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Slf4j
@Service("proxyFailedUserService")
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Qualifier("proxyFailedUserService")
    private UserService self;

    @PostConstruct
    public void init() {
        log.info("this {} self {}", this.getClass().getName(), self.getClass().getName());
    }

    //私有方法
    public int createUserWrong1(String name) {
        try {
            this.createUserPrivate(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    //自调用
    public int createUserWrong2(String name) {
        try {
            this.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    @Transactional
    void createUserPrivate(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test")) { throw new RuntimeException("invalid username!"); }
    }

    //可以传播出异常
    @Transactional
    public void createUserPublic(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test")) { throw new RuntimeException("invalid username!"); }
    }

    //重新注入自己
    public int createUserRight(String name) {
        try {
            self.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    //不出异常
    @Transactional
    public int createUserWrong3(String name) {
        try {
            this.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }

}
