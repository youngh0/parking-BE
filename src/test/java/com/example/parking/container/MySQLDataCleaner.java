package com.example.parking.container;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.google.common.base.CaseFormat;

import java.util.List;

@Component
public class MySQLDataCleaner {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void delete() {
        List<String> tableNames = entityManager.getMetamodel().getEntities().stream()
                .map(table -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, table.getName()))
                .toList();

//        jdbcTemplate.execute("TRUNCATE TABLE auth_code");
//        jdbcTemplate.execute("TRUNCATE TABLE parking");
//        jdbcTemplate.execute("TRUNCATE TABLE member_session");
//        jdbcTemplate.execute("TRUNCATE TABLE search_condition");
//        jdbcTemplate.execute("TRUNCATE TABLE member");
        setForeignKeyEnabled(false);
        tableNames.forEach(table -> jdbcTemplate.execute("TRUNCATE table " + table));
        setForeignKeyEnabled(true);
    }

    private void setForeignKeyEnabled(boolean enabled) {
        entityManager.createNativeQuery("SET foreign_key_checks = " + enabled).executeUpdate();
    }
}
