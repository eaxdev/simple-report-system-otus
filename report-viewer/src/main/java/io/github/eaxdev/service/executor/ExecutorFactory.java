package io.github.eaxdev.service.executor;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.eaxdev.model.ConnectionInfo;
import io.github.eaxdev.model.ConnectionProperty;
import io.github.eaxdev.service.ConnectionService;
import io.github.eaxdev.service.DataModelService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ExecutorFactory {

    private final DataModelService dataModelService;
    private final ConnectionService connectionService;

    private final Map<Integer, Executor> cache = new ConcurrentHashMap<>();

    public Executor getExecutor(int modelId) {
        return cache.computeIfAbsent(modelId, k -> {
            var dataModel = dataModelService.findById(modelId);
            var connection = connectionService.findById(dataModel.getConnectionId());
            return create(connection);
        });
    }

    public Executor create(ConnectionInfo info) {
        List<ConnectionProperty> connectionProperties = info.getConnectionProperties();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("Pool-`" + info.getName() + "`");
        hikariConfig.setJdbcUrl(jdbcUrl(info));
        hikariConfig.setUsername(info.getUser());
        hikariConfig.setPassword(info.getPassword());
        hikariConfig.setDriverClassName("ru.yandex.clickhouse.ClickHouseDriver");
        hikariConfig.setMinimumIdle(0);
        hikariConfig.setConnectionTimeout(TimeUnit.SECONDS.toMillis(1));
        hikariConfig.setIdleTimeout(TimeUnit.MINUTES.toMillis(5));
        hikariConfig.setMaxLifetime(TimeUnit.MINUTES.toMillis(10));
        hikariConfig.setMaximumPoolSize(10);
        connectionProperties.forEach(p -> hikariConfig.addDataSourceProperty(p.getName(), p.getValue()));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return new JdbcExecutor(new JdbcTemplate(dataSource));
    }

    private String jdbcUrl(ConnectionInfo con) {
        final String dbName = StringUtils.isEmpty(con.getDatabase()) ? StringUtils.EMPTY : con.getDatabase();
        return String.format("jdbc:clickhouse://%s:%d/%s", con.getHost(), con.getPort(), dbName);
    }

}
