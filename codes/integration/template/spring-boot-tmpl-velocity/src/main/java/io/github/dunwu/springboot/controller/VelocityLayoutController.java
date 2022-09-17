package io.github.dunwu.springboot.controller;

import com.alibaba.boot.velocity.VelocityLayoutProperties;
import com.alibaba.boot.velocity.annotation.VelocityLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link VelocityLayout} {@link Controller}
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">taogu.mxx</a> (Office)
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see BaseController
 * @since 2016.12.21
 */
@Controller
@VelocityLayout("/layout/default.vm")
public class VelocityLayoutController extends BaseController {

    /**
     * To set required = false for Test Case
     */
    @Autowired(required = false)
    private VelocityLayoutProperties velocityLayoutProperties = new VelocityLayoutProperties();

    @RequestMapping(value = { "/layout" })
    @VelocityLayout("/layout/layout.vm")
    public String layout(Model model) {
        // Overrides @VelocityLayout("/layout/layout.vm")
        // spring.velocity.layoutKey = layout_key
        model.addAttribute(velocityLayoutProperties.getLayoutKey(), "/layout/layout.vm");
        return "index";
    }

    @RequestMapping("/layout2")
    @VelocityLayout("/layout/layout2.vm") // Overrides @VelocityLayout("/layout/default.vm")
    public String layout2(Model model) {
        return "index";
    }

    @RequestMapping("/layout3") // Uses @VelocityLayout("/layout/default.vm")
    public String layout3(Model model) {
        return "index";
    }

}
