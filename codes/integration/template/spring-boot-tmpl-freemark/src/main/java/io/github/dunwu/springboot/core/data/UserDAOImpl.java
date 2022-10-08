package io.github.dunwu.springboot.core.data;

import freemarker.template.TemplateException;
import io.github.dunwu.springboot.FreemarkHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDAOImpl implements UserDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FreemarkHelper freemarkHelper;

    @Override
    public void clear() {
        Map params = new HashMap();
        params.put("tableName", "User");

        String sqlStatement = getSqlStatement(FreemarkHelper.TMPL_SQL_CLEAR, params);
        jdbcTemplate.execute(sqlStatement);
    }

    @Override
    public void create(String name, Integer age) {
        Map params = new HashMap();
        params.put("tableName", "user");
        params.put("key1", "name");
        params.put("key2", "age");
        params.put("value1", name);
        params.put("value2", age);

        String sqlStatement = getSqlStatement(FreemarkHelper.TMPL_SQL_CREATE, params);
        jdbcTemplate.execute(sqlStatement);
    }

    @Override
    public void deleteByName(String name) {
        Map params = new HashMap();
        params.put("tableName", "User");
        params.put("name", name);

        String sqlStatement = getSqlStatement(FreemarkHelper.TMPL_SQL_DELETE, params);
        jdbcTemplate.execute(sqlStatement);
    }

    @Override
    public List<User> list() {
        Map params = new HashMap();
        params.put("tableName", "User");

        String sqlStatement = getSqlStatement(FreemarkHelper.TMPL_SQL_SELECT, params);
        return jdbcTemplate.query(sqlStatement, new BeanPropertyRowMapper(User.class));
    }

    private String getSqlStatement(String tmplSqlClear, Map params) {
        String sqlStatement = null;
        try {
            sqlStatement = freemarkHelper.getMergeContent(tmplSqlClear, params);
        } catch (IOException e) {
            log.error("Freemarker IOException:", e);
        } catch (TemplateException e) {
            log.error("Freemarker TemplateException:", e);
        }
        return sqlStatement;
    }

}
