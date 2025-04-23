package com.beiming.es.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "consumer_credit_log")
public class ConsumerCreditLog implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "log_uuid")
    private String logUuid;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "channel_type")
    private String channelType;
    @Column(name = "channel_name")
    private String channelName;
    @Column(name = "param")
    private String param;
    @Column(name = "result_code")
    private String resultCode;
    @Column(name = "check_result")
    private String checkResult;
    @Column(name = "result_content")
    private String resultContent;
    @Column(name = "cache_state")
    private String cacheState;
    //计费状态，0未计费，1不计费，2已计费
    @Column(name = "bill_state")
    private String billState;
    @Column(name = "price")
    private double price;
    @Column(name = "delay")
    private int delay;
    @Transient
    private String date;
    @Transient
    private String appName;
    @Transient
    private String channelTypeName;
    @Transient
    private String channelNameDto;
    @Transient
    private String oldChannelType;

    //0未删除，1已删除
    @Column(name = "del")
    private String del = "0";
    @Column(name = "create_time")
    @Basic
    private Date createTime;
    @Column(name = "update_time")
    @UpdateTimestamp
    @Basic
    private Date updateTime;
}