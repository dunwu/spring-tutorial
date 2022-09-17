package io.github.dunwu.springboot.security.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.dunwu.springboot.security.system.entity.RoleMenu;

import java.util.List;

/**
 * @author MrBird
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 通过角色 id 删除
     *
     * @param roleIds 角色 id
     */
    void deleteRoleMenusByRoleId(List<String> roleIds);

    /**
     * 通过菜单（按钮）id 删除
     *
     * @param menuIds 菜单（按钮）id
     */
    void deleteRoleMenusByMenuId(List<String> menuIds);
}
