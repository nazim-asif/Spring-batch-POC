package com.springbatch.springbatchpoc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class CustomerCustomRepo {
    private final JdbcTemplate jdbcTemplate;
    public List<Map<String, Object>> findEntitiesByCriteria( ) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM CUSTOMERS_INFO limit 10000");
//        List<Object> args = new ArrayList<>();
//        if (criteria.getName() != null) {
//            sb.append(" WHERE name = ?");
//            args.add(criteria.getName());
//        }
//        if (criteria.getAge() != null) {
//            if (args.isEmpty()) {
//                sb.append(" WHERE");
//            } else {
//                sb.append(" AND");
//            }
//            sb.append(" age = ?");
//            args.add(criteria.getAge());
//        }
        String sql = sb.toString();
//        Object[] argsArray = args.toArray(new Object[0]);
        return jdbcTemplate.queryForList(sql);
    }


}
