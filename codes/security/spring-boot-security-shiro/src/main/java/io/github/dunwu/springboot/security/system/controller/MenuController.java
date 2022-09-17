package io.github.dunwu.springboot.security.system.controller;

import com.wuwenze.poi.ExcelKit;
import io.github.dunwu.admin.common.annotation.ControllerEndpoint;
import io.github.dunwu.admin.common.constant.DunwuResponse;
import io.github.dunwu.admin.common.constant.MenuTree;
import io.github.dunwu.admin.common.controller.BaseController;
import io.github.dunwu.admin.common.exception.FebsException;
import io.github.dunwu.springboot.security.system.entity.Menu;
import io.github.dunwu.springboot.security.system.entity.User;
import io.github.dunwu.springboot.security.system.service.IMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("menu")
public class MenuController extends BaseController {

    private final IMenuService menuService;

    @GetMapping("{username}")
    public DunwuResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) throws
        FebsException {
        User currentUser = getCurrentUser();
        if (!StringUtils.equalsIgnoreCase(username, currentUser.getUsername())) {
            throw new FebsException("您无权获取别人的菜单");
        }
        MenuTree<Menu> userMenus = this.menuService.findUserMenus(username);
        return new DunwuResponse().data(userMenus);
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取菜单树失败")
    public DunwuResponse getMenuTree(Menu menu) {
        MenuTree<Menu> menus = this.menuService.findMenus(menu);
        return new DunwuResponse().success().data(menus.getChilds());
    }

    @PostMapping
    @RequiresPermissions("menu:add")
    @ControllerEndpoint(operation = "新增菜单/按钮", exceptionMessage = "新增菜单/按钮失败")
    public DunwuResponse addMenu(@Valid Menu menu) {
        this.menuService.createMenu(menu);
        return new DunwuResponse().success();
    }

    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    @ControllerEndpoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public DunwuResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        this.menuService.deleteMeuns(menuIds);
        return new DunwuResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("menu:update")
    @ControllerEndpoint(operation = "修改菜单/按钮", exceptionMessage = "修改菜单/按钮失败")
    public DunwuResponse updateMenu(@Valid Menu menu) {
        this.menuService.updateMenu(menu);
        return new DunwuResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("menu:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Menu menu, HttpServletResponse response) {
        List<Menu> menus = this.menuService.findMenuList(menu);
        ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
    }
}
