package io.github.dunwu.springboot;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang Peng
 * @since 2019-01-07
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository customerRepository;

    @ResponseBody
    @RequestMapping(value = "/user/add")
    public ResponseDTO<User> add(User user) {
        User result = customerRepository.save(user);
        List<User> list = new ArrayList<>();
        list.add(result);
        return new ResponseDTO<>(true, 1, list);
    }

    @ResponseBody
    @RequestMapping(value = "/user/delete")
    public ResponseDTO delete(@RequestParam("id") Long id) {
        customerRepository.deleteById(id);
        return new ResponseDTO<>(true, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/user/list")
    public ResponseDTO<User> list() {
        Iterable<User> all = customerRepository.findAll();
        List<User> list = IteratorUtils.toList(all.iterator());
        return new ResponseDTO<>(true, list.size(), list);
    }

    @ResponseBody
    @RequestMapping(value = "/user/save")
    public ResponseDTO<User> save(@RequestParam("id") Long id, User user) {
        user.setId(id);
        customerRepository.save(user);
        List<User> list = new ArrayList<>();
        list.add(user);
        return new ResponseDTO<>(true, 1, list);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        return "user";
    }

}
