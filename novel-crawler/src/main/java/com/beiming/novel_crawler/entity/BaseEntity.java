package com.beiming.novel_crawler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * BaseEntity
 */
@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_at", insertable = false, updatable = false, columnDefinition = "datetime default current_timestamp comment '创建时间'")
    private LocalDateTime createAt;

    @Column(name = "update_at", insertable = false, columnDefinition = "datetime default current_timestamp on update current_timestamp comment '创建时间'")

    private LocalDateTime updateAt;

}
