package example.spring.core.aop;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * log_record 表 Dao 接口
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-18
 */
public interface LogRecordDao {

    // DML
    // -------------------------------------------------------------------
    void insert(LogRecord logRecord);

    void deleteAll();

    Integer count();

    List<LogRecord> list();

    JdbcTemplate getJdbcTemplate();

    // DDL
    // -------------------------------------------------------------------
    void truncate();

}
