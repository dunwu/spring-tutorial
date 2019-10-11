package io.github.dunwu.web.jsp;

import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-05
 */
public class OverrideTag extends BodyTagSupport {

	private static final long serialVersionUID = 1692569211455126528L;

	private static final String BLOCK = "__jsp_override__";

	protected String name;

	@Override
	public int doStartTag() {
		return isOverrided() ? SKIP_BODY : EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() {
		if (isOverrided()) {
			return EVAL_PAGE;
		}
		BodyContent b = getBodyContent();
		String newName = BLOCK + name;

		pageContext.getRequest().setAttribute(newName, b.getString());
		return EVAL_PAGE;
	}

	public void setName(String name) {
		this.name = name;
	}

	private boolean isOverrided() {
		String newName = BLOCK + name;
		return pageContext.getRequest().getAttribute(newName) != null;
	}

}
