package com.beiming.clickhouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ClickHouseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryData(String query) {
        return jdbcTemplate.queryForList(query);
    }

    public int updateData(String updateQuery) {
        return jdbcTemplate.update(updateQuery);
    }
}
