package io.github.dunwu.springboot.security.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import io.github.dunwu.admin.common.annotation.ControllerEndpoint;
import io.github.dunwu.admin.common.constant.DeptTree;
import io.github.dunwu.admin.common.constant.DunwuResponse;
import io.github.dunwu.admin.common.constant.QueryRequest;
import io.github.dunwu.admin.common.exception.FebsException;
import io.github.dunwu.springboot.security.system.entity.Dept;
import io.github.dunwu.springboot.security.system.service.IDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("dept")
public class DeptController {

    private final IDeptService deptService;

    @GetMapping("select/tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public List<DeptTree<Dept>> getDeptTree() throws FebsException {
        return this.deptService.findDepts();
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public DunwuResponse getDeptTree(Dept dept) throws FebsException {
        List<DeptTree<Dept>> depts = this.deptService.findDepts(dept);
        return new DunwuResponse().success().data(depts);
    }

    @PostMapping
    @RequiresPermissions("dept:add")
    @ControllerEndpoint(operation = "新增部门", exceptionMessage = "新增部门失败")
    public DunwuResponse addDept(@Valid Dept dept) {
        this.deptService.createDept(dept);
        return new DunwuResponse().success();
    }

    @GetMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    @ControllerEndpoint(operation = "删除部门", exceptionMessage = "删除部门失败")
    public DunwuResponse deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws FebsException {
        String[] ids = deptIds.split(StringPool.COMMA);
        this.deptService.deleteDepts(ids);
        return new DunwuResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("dept:update")
    @ControllerEndpoint(operation = "修改部门", exceptionMessage = "修改部门失败")
    public DunwuResponse updateDept(@Valid Dept dept) throws FebsException {
        this.deptService.updateDept(dept);
        return new DunwuResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("dept:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws FebsException {
        List<Dept> depts = this.deptService.findDepts(dept, request);
        ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
    }
}
