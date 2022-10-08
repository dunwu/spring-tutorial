package io.github.dunwu.springboot.shell;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.io.AnsiColorUtil;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-13
 */
@ShellComponent
public class JsonCommand {

    @ShellMethod("JSON 序列化")
    public void tojson(Map<String, Object> map) {
        AnsiColorUtil.BLUE.println(JSONUtil.toJsonStr(map));
    }

}
