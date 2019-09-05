package io.github.dunwu.web.jsp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-05
 */
public class BlockTag extends TagSupport {

    private static final long serialVersionUID = 5981092740359798010L;
    private static final String BLOCK = "__jsp_override__";

    protected String name;

    @Override
    public int doStartTag() {
        return getOverriedContent() == null ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        String overriedContent = getOverriedContent();
        if (overriedContent == null) {
            return EVAL_PAGE;
        }

        try {
            pageContext.getOut().write(overriedContent);
        } catch (IOException e) {
            throw new JspException("try to override jsp content failed, block name:" + name, e);
        }
        return EVAL_PAGE;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getOverriedContent() {
        String newName = BLOCK + name;
        return (String) pageContext.getRequest().getAttribute(newName);
    }
}
