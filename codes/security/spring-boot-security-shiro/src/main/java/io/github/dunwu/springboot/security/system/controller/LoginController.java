package io.github.dunwu.springboot.security.system.controller;

import io.github.dunwu.admin.common.constant.DunwuResponse;
import io.github.dunwu.admin.common.controller.BaseController;
import io.github.dunwu.admin.common.exception.FebsException;
import io.github.dunwu.admin.common.service.CaptchaService;
import io.github.dunwu.admin.common.utils.Md5Util;
import io.github.dunwu.admin.monitor.entity.LoginLog;
import io.github.dunwu.admin.monitor.service.ILoginLogService;
import io.github.dunwu.springboot.security.system.entity.User;
import io.github.dunwu.springboot.security.system.service.IUserService;
import io.github.dunwu.common.annotation.FlowLimit;
import io.github.dunwu.util.net.IpUtils;
import io.github.dunwu.web.util.ServletUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Validated
@RestController
@RequiredArgsConstructor
public class LoginController extends BaseController {

    private final IUserService userService;

    private final CaptchaService captchaService;

    private final ILoginLogService loginLogService;

    @PostMapping("login")
    @FlowLimit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
    public DunwuResponse login(
        @NotBlank(message = "{required}") String username,
        @NotBlank(message = "{required}") String password,
        @NotBlank(message = "{required}") String verifyCode,
        boolean rememberMe, HttpServletRequest request) throws FebsException {
        HttpSession session = request.getSession();
        captchaService.check(session.getId(), verifyCode);
        password = Md5Util.encrypt(username.toLowerCase(), password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        super.login(token);
        // 保存登录日志
        ServletUtil.RequestIdentityInfo identityInfo = ServletUtil.getRequestIdentityInfo(request);
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setIp(identityInfo.getIp());
        loginLog.setLocation(IpUtils.getRegionName(identityInfo.getIp()));
        loginLog.setSystem(identityInfo.getSystem());
        loginLog.setBrowser(identityInfo.getBrowser());
        this.loginLogService.saveLoginLog(loginLog);
        return new DunwuResponse().success();
    }

    @PostMapping("regist")
    public DunwuResponse regist(
        @NotBlank(message = "{required}") String username,
        @NotBlank(message = "{required}") String password) throws FebsException {
        User user = userService.findByName(username);
        if (user != null) {
            throw new FebsException("该用户名已存在");
        }
        this.userService.regist(username, password);
        return new DunwuResponse().success();
    }

    @GetMapping("index/{username}")
    public DunwuResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        // 更新登录时间
        this.userService.updateLoginTime(username);
        Map<String, Object> data = new HashMap<>(5);
        // 获取系统访问记录
        Long totalVisitCount = this.loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = this.loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = this.loginLogService.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = this.loginLogService.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = this.loginLogService.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return new DunwuResponse().success().data(data);
    }

    @GetMapping("images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, FebsException {
        captchaService.create(request, response);
    }

}
