package io.github.eaxdev.service.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
public class JdbcExecutor implements Executor {

    private final JdbcTemplate jdbcTemplate;

    public JdbcExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> getData(String query) {
        log.debug("Execute query: {}", query);
        return jdbcTemplate.queryForList(query);
    }

    @Override
    public Integer getCount(String query) {
        log.debug("Execute query: {}", query);
        return jdbcTemplate.queryForObject(query, Integer.class);
    }
}
