package com.springbatch.springbatchpoc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class WritterCustomRepo {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void findEntitiesByCriteria(List list ) {
         saveEntities(list, "customers_bkp");
    }

    public void saveEntities(List<Map<String, Object>> entities, String tableName) {
        Map<String, Object> firstEntity = entities.get(0);
        String sql = generateInsertSql(tableName, firstEntity.keySet());
        for (Map<String, Object> entity : entities) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            entity.forEach(parameters::addValue);
            namedParameterJdbcTemplate.update(sql, parameters);
        }
    }

    private String generateInsertSql(String tableName, Set<String> columnNames) {
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner values = new StringJoiner(", ");
        for (String columnName : columnNames) {
            columns.add(columnName);
            values.add(":" + columnName);
        }
        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
    }


}
