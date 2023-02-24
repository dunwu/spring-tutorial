package example.spring.web.controller;

import cn.hutool.core.util.StrUtil;
import example.spring.web.dto.MenuDTO;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * 配合前端请求的 API 接口
 *
 * @author zhangpeng0913
 * @since 2017/8/23.
 */
@RestController
public class ApiController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public DataListResult<MenuDTO> getAll(HttpServletRequest request) {
        String data = request.getParameter("data");
        log.debug("recv data = {}", data);
        return DataListResult.ok(getAll());
    }

    private static List<MenuDTO> getAll() {
        MenuDTO item0 = new MenuDTO("0", "首页", "home", "Item", "/pages/home");

        MenuDTO subMenu1 = new MenuDTO("1", "业务", "bars", "SubMenu", null);
        MenuDTO item11 = new MenuDTO("11", "Mailbox", "mail", "Item", "/pages/mailbox");
        MenuDTO item12 = new MenuDTO("12", "用户列表", "user", "Item", "/pages/user");
        subMenu1.addChild(item11);
        subMenu1.addChild(item12);

        MenuDTO subMenu2 = new MenuDTO("2", "Others", "coffee", "SubMenu", null);
        MenuDTO itemGroup1 = new MenuDTO("21", "Group1", "windows-o", "ItemGroup", null);
        MenuDTO item22 = new MenuDTO("22", "Group1-1", null, "Item", null);
        MenuDTO divider = new MenuDTO("23", "Divider1", null, "Divider", null);
        MenuDTO itemGroup2 = new MenuDTO("24", "Group2", "apple-o", "ItemGroup", null);
        MenuDTO item25 = new MenuDTO("25", "Group2-1", null, "Item", null);
        itemGroup1.addChild(item22);
        itemGroup2.addChild(item25);
        subMenu2.addChild(itemGroup1);
        subMenu2.addChild(divider);
        subMenu2.addChild(itemGroup2);

        List<MenuDTO> menus = new ArrayList<>();
        menus.add(item0);
        menus.add(subMenu1);
        menus.add(subMenu2);

        return menus;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public DataResult<Map<String, String>> login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        if (StrUtil.equals(username, "admin") && StrUtil.equals(password, "123456")) {
            Map<String, String> result = new HashMap<>(3);
            result.put("name", "admin");
            result.put("role", "ADMIN");
            result.put("uid", "1");
            return DataResult.ok(result);
        } else {
            return DataResult.fail(ResultStatus.FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public DataResult<?> logout() {
        return DataResult.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public DataResult<Map<String, String>> my() {
        Map<String, String> map = new HashMap<>(3);
        map.put("name", "admin");
        map.put("role", "ADMIN");
        map.put("uid", "1");
        return DataResult.ok(map);
    }

}
