package com.springbatch.springbatchpoc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class WritterCustomRepo {
    private final JdbcTemplate jdbcTemplate;
    public List<Map<String, Object>> findEntitiesByCriteria( ) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM CUSTOMERS_INFO limit 10000");

        String sql = sb.toString();
        return jdbcTemplate.queryForList(sql);
    }


}
