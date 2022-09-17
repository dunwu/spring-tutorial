package io.github.dunwu.springboot;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author Zhang Peng
 * @since 2018-12-19
 */
@Component
public class FreemarkHelper {

    public static final String TMPL_SQL_SELECT = "sql/sql_select.ftl";

    public static final String TMPL_SQL_CLEAR = "sql/sql_clear.ftl";

    public static final String TMPL_SQL_DELETE = "sql/sql_delete.ftl";

    public static final String TMPL_SQL_CREATE = "sql/sql_create.ftl";

    @Autowired
    private Configuration freemarkConfig;

    public String getMergeContent(final String tmplName, Map params) throws IOException, TemplateException {
        /* Get the template (uses cache internally) */
        Template temp = freemarkConfig.getTemplate(tmplName);

        /* Merge data-model with template */
        StringWriter stringWriter = new StringWriter();
        temp.process(params, stringWriter);
        return stringWriter.toString();
    }

}
