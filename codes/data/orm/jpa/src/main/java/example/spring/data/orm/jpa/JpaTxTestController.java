package example.spring.data.orm.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpa/tx/test")
@Slf4j
public class JpaTxTestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public void clearUser() {
        userRepository.deleteByName("test");
    }

    public User initTestUser() {
        return new User("test", 24, "辽宁", "user4@163.com");
    }

    @GetMapping("fail1")
    public void fail1() {
        clearUser();
        User user = initTestUser();
        userService.fail1(user);
    }

    @GetMapping("fail2")
    public void fail2() {
        clearUser();
        User user = initTestUser();
        try {
            userService.fail2(user);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
    }

    @GetMapping("fail3")
    public void fail3() {
        clearUser();
        User user = initTestUser();
        try {
            userService.fail3(user);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
    }

    @GetMapping("fail4")
    public void fail4() {
        clearUser();
        User user = initTestUser();
        try {
            userService.fail4(user);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
    }

    @GetMapping("fail5")
    public void fail5() {
        clearUser();
        User user = initTestUser();
        userService.fail5(user);
    }

    // =====================================================
    // 此处分界
    // 上面的方法为触发事务回滚失败的例子；
    // 下面的方法为触发事务回滚成功的例子。
    // =====================================================

    @GetMapping("ok1")
    public void ok1() {
        clearUser();
        User user = initTestUser();
        try {
            userService.ok1(user);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
    }

    @GetMapping("ok2")
    public void ok2() {
        clearUser();
        User user = initTestUser();
        try {
            userService.ok2(user);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
    }

    @GetMapping("ok3")
    public void ok3() {
        clearUser();
        User user = initTestUser();
        try {
            userService.ok3(user);
        } catch (Exception ex) {
            log.error("createUserWrong failed, reason:{}", ex.getMessage());
        }
    }

    @GetMapping("ok4")
    public void ok4() {
        clearUser();
        User user = initTestUser();
        try {
            userService.ok4(user);
        } catch (Exception ex) {
            log.error("createUserWrong2 failed, reason:{}", ex.getMessage(), ex);
        }
    }

}
