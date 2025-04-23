package com.beiming.novel_crawler.spider.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;

import java.sql.ResultSet;

/**
 * MysqlCacheQueueScheduler
 */
public class MysqlQueueScheduler implements MonitorableScheduler {
    private final static Logger log = LoggerFactory.getLogger(MysqlQueueScheduler.class);
    private final JdbcTemplate jdbcTemplate;

    private final static String STATUS_READY = "READY";
    private final static String STATUS_DOING = "DOING";
    private final static String STATUS_SUCCESS = "SUCCESS";
    private final static String STATUS_FAIL = "FAIL";

    private static final String createSql = """
            create table  if not exists url_set(
                id bigint not null auto_increment,
                url varchar(768) not null comment '请求地址',
                `status` varchar(20) not null default 'ready' comment 'ready就绪,requesting请求中,success完成,fail最终失败',
                request_time datetime comment '请求时间',
                priority int(11) not null default '1' comment '优先级',
                primary key (id),
                index `idx_status&priority`(`status`,priority) using btree,
                unique index uk_url(url) using hash
            )
            """;
    private static final String existsSql = "select count(1) from url_set where url = ?";
    private static final String insertSql = "insert into url_set(url,`status`,priority) values(?,?,?)";
    private static final String totalSql = "select count(1) from url_set";
    private static final String getRequestSql = "select url from url_set where `status`= '%s' order by id, priority limit  1".formatted(STATUS_READY);
    private static final String updateDoingSql = "update url_set set `status` = '%s' ,request_time = now()  where url = ? and `status` = '%s' ".formatted(STATUS_DOING, STATUS_READY);
    private static final String updateSuccessSql = "update url_set set `status` = '%s'  where url = ? and `status` = '%s'".formatted(STATUS_SUCCESS, STATUS_DOING);
    private static final String updateRetrySql = "update url_set set `status` = if(priority = 5,'%s','%s'),priority = if(priority = 5, 999 ,priority + 1) where url = ? and `status` = '%s'".formatted(STATUS_FAIL, STATUS_READY, STATUS_DOING);
    private static final String readyCountSql = "select count(1) from url_set where `status` <> '%s'".formatted(STATUS_READY);

    public MysqlQueueScheduler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        init();
    }

    private void init() {
        if (null != jdbcTemplate) {
            jdbcTemplate.execute(createSql);
        } else {
            throw new RuntimeException("jdbcTemplate is null");
        }

    }

    @Override
    public int getLeftRequestsCount(Task task) {
        Integer count = jdbcTemplate.queryForObject(readyCountSql, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        Integer count = jdbcTemplate.queryForObject(totalSql, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void push(Request request, Task task) {
        if (request.getUrl() != null && !request.getUrl().trim().isEmpty()) {
            try {
                String status = request.getExtra("status");
                if (status == null) {
                    status = "other";
                }
                switch (status) {
                    case STATUS_SUCCESS:
                        jdbcTemplate.update(updateSuccessSql, request.getUrl());
                        break;
                    case STATUS_FAIL:
                        jdbcTemplate.update(updateRetrySql, request.getUrl());
                        break;
                    case STATUS_READY:
                    default:
                        Integer count = jdbcTemplate.queryForObject(existsSql, Integer.class, request.getUrl());
                        if (count == null || count == 0) {
                            jdbcTemplate.update(insertSql, request.getUrl(), STATUS_READY, 1);
                        }
                        log.debug("Url: {}", request.getUrl());
                        break;
                }
            } catch (Exception e) {
                log.error("插入URL[{}]错误, 原因: {}", request.getUrl(), e.getMessage());
            }
        }
    }

    @Override
    public Request poll(Task task) {
        Request request;
        String url = jdbcTemplate.query(getRequestSql, (ResultSet rs) -> {
            if (rs.next()) {
                return rs.getString("url");
            } else {
                return null;
            }
        });
        if (url != null) {
            int update = jdbcTemplate.update(updateDoingSql, url);
            while (update == 0) {
                url = jdbcTemplate.query(getRequestSql, (ResultSet rs) -> {
                    if (rs.next()) {
                        return rs.getString("url");
                    } else {
                        return null;
                    }
                });
                if (url != null) {
                    update = jdbcTemplate.update(updateDoingSql, url);
                } else {
                    update = 1;
                }
            }
        }
        if (url == null) {
            request = null;
        } else {
            request = new Request(url);
        }
        return request;
    }

}
