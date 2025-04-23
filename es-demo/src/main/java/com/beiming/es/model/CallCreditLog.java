package com.beiming.es.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "call_credit_log")
public class CallCreditLog implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "log_uuid")
    private String logUuid;
    @Column(name = "channel_type")
    private String channelType;
    @Column(name = "channel_name")
    private String channelName;
    @Column(name = "head")
    private String head;
    @Column(name = "body")
    private String body;
    @Column(name = "result")
    private String result;
    @Column(name = "delay")
    private int delay;
    @Column(name = "bill_state")
    private String billState;
    @Column(name = "result_code")
    private String resultCode;
    @Column(name = "price")
    private Double price;
    @Column(name = "mapping_result")
    private String mappingResult;

    @Transient
    private String channelTypeName;
    @Transient
    private String channelNameDto;


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