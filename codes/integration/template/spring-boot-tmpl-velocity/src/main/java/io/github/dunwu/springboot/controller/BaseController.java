package io.github.dunwu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Base {@link Controller}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">taogu.mxx</a> (Office)
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see Controller
 * @since 2016.12.21
 */
public abstract class BaseController {

    @ModelAttribute("pageTitle")
    public String pageTitle() {
        return "页面标题";
    }

    @ModelAttribute("linkLabel")
    public String linkLabel() {
        return "阿里巴巴";
    }

    @ModelAttribute("link")
    public String link() {
        return "https://www.alibaba-inc.com";
    }

}
