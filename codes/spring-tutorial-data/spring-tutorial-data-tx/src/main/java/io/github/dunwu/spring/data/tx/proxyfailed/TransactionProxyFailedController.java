package io.github.dunwu.spring.data.tx.proxyfailed;

import io.github.dunwu.spring.data.tx.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("proxyfailed")
@Slf4j
public class TransactionProxyFailedController {

	@Autowired
	@Qualifier("proxyFailedUserService")
	private UserService userService;

	@GetMapping("wrong1")
	public int wrong1(@RequestParam("name") String name) {
		return userService.createUserWrong1(name);
	}

	@GetMapping("wrong2")
	public int wrong2(@RequestParam("name") String name) {
		return userService.createUserWrong2(name);
	}

	@GetMapping("wrong3")
	public int wrong3(@RequestParam("name") String name) {
		return userService.createUserWrong3(name);
	}

	@GetMapping("right1")
	public int right1(@RequestParam("name") String name) {
		return userService.createUserRight(name);
	}

	@GetMapping("right2")
	public int right2(@RequestParam("name") String name) {
		try {
			userService.createUserPublic(new UserEntity(name));
		} catch (Exception ex) {
			log.error("create user failed because {}", ex.getMessage());
		}
		return userService.getUserCount(name);
	}

}
