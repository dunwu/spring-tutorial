package io.github.dunwu.springboot.security.system.controller;

import com.wuwenze.poi.ExcelKit;
import io.github.dunwu.admin.common.annotation.ControllerEndpoint;
import io.github.dunwu.admin.common.constant.DunwuResponse;
import io.github.dunwu.admin.common.constant.QueryRequest;
import io.github.dunwu.admin.common.controller.BaseController;
import io.github.dunwu.admin.common.exception.FebsException;
import io.github.dunwu.springboot.security.system.entity.Role;
import io.github.dunwu.springboot.security.system.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("role")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    @GetMapping
    public DunwuResponse getAllRoles(Role role) {
        return new DunwuResponse().success().data(roleService.findRoles(role));
    }

    @GetMapping("list")
    @RequiresPermissions("role:view")
    public DunwuResponse roleList(Role role, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.roleService.findRoles(role, request));
        return new DunwuResponse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("role:add")
    @ControllerEndpoint(operation = "新增角色", exceptionMessage = "新增角色失败")
    public DunwuResponse addRole(@Valid Role role) {
        this.roleService.createRole(role);
        return new DunwuResponse().success();
    }

    @GetMapping("delete/{roleIds}")
    @RequiresPermissions("role:delete")
    @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除角色失败")
    public DunwuResponse deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        this.roleService.deleteRoles(roleIds);
        return new DunwuResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("role:update")
    @ControllerEndpoint(operation = "修改角色", exceptionMessage = "修改角色失败")
    public DunwuResponse updateRole(Role role) {
        this.roleService.updateRole(role);
        return new DunwuResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("role:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Role role, HttpServletResponse response) throws FebsException {
        List<Role> roles = this.roleService.findRoles(role, queryRequest).getRecords();
        ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
    }

}
