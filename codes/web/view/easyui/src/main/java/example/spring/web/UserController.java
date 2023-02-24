package example.spring.web;

import org.apache.commons.collections4.IteratorUtils;
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
    public ResponseDto<User> add(User user) {
        User result = customerRepository.save(user);
        List<User> list = new ArrayList<>();
        list.add(result);
        return new ResponseDto<>(true, 1, list);
    }

    @ResponseBody
    @RequestMapping(value = "/user/delete")
    public ResponseDto delete(@RequestParam("id") Long id) {
        customerRepository.deleteById(id);
        return new ResponseDto<>(true, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/user/list")
    public ResponseDto<User> list() {
        Iterable<User> all = customerRepository.findAll();
        List<User> list = IteratorUtils.toList(all.iterator());
        return new ResponseDto<>(true, list.size(), list);
    }

    @ResponseBody
    @RequestMapping(value = "/user/save")
    public ResponseDto<User> save(@RequestParam("id") Long id, User user) {
        user.setId(id);
        customerRepository.save(user);
        List<User> list = new ArrayList<>();
        list.add(user);
        return new ResponseDto<>(true, 1, list);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        return "user";
    }

}
