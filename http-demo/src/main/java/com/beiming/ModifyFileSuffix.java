package com.beiming;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;

/**
 * @author lcl
 * @date 2024/3/12 15:54
 */
public class ModifyFileSuffix {
    static File dir = new File("C:\\Users\\lcl\\Downloads\\photo");

    public static void main(String[] args) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (file.getName().split("\\.").length == 1) {
                    file.renameTo(new File(dir, file.getName() + ".png"));
                }
            }
        }
    }

    @NotNull
    private static JdbcTemplate getJdbcTemplate() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setAutoCommit(true);
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("goudan125");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
