package be.ucll.util;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Initializes an H2 database to "read uncommitted" transaction isolation level.
 *
 * @author Koen Serneels
 */
public class H2IsolationLevelInitializerBean {

    private final JdbcTemplate jdbcTemplate;

    public H2IsolationLevelInitializerBean(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setIsolationLevelReadUncommited() {
        jdbcTemplate.execute("SET LOCK_MODE 0");
    }
}
