package example.spring.core.aop;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * user 表 Dao 接口实现类
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-18
 */
@Repository
public class LogRecordDaoImpl implements LogRecordDao {

    private final JdbcTemplate jdbcTemplate;

    public LogRecordDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(LogRecord logRecord) {
        jdbcTemplate.update("INSERT INTO log_record(description, level, exception, method, params, request_time) "
                + "VALUES(?, ?, ?, ?, ?, ?)",
            logRecord.getDescription(), logRecord.getLevel(), logRecord.getException(),
            logRecord.getMethod(), logRecord.getParams(), logRecord.getRequestTime());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        jdbcTemplate.execute("DELETE FROM log_record");
    }

    @Override
    public Integer count() {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM log_record", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<LogRecord> list() {
        return jdbcTemplate.query("SELECT * FROM log_record", new BeanPropertyRowMapper(LogRecord.class));
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void truncate() {
        jdbcTemplate.execute("TRUNCATE TABLE log_record");
    }

}
