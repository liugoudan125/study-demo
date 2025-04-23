package com.beiming.es.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CallLog
 */

/**
 * 调用上游征信接口日志 银行卡三要素，手机在网时长，手机号姓名等
 */
@Document(indexName = "call-log", dynamic = Dynamic.TRUE)
public class CallLog {
    /**
     * ID
     */
    @Field(type = FieldType.Keyword)
    private Long id;

    /**
     * 调用日志关联
     */
    @Field(name = "log_uuid", type = FieldType.Keyword)
    private String logUuid;

    /**
     * 接口类型
     */
    private String channelType;

    /**
     * 接口名称
     */
    private String channelName;

    /**
     * 请求头
     */
    private String head;

    /**
     * 请求体 json格式
     */
    private String body;

    /**
     * 调用结果
     */
    private String result;

    /**
     * 删除状态 0正常,1删除
     */
    private String del;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private Integer delay;

    /**
     * 收费状态，0预付，1不计费，2后付
     */
    private String billState;

    /**
     * 返回状态码:成功,失败,超时
     */
    private String resultCode;

    private BigDecimal price;

    /**
     * 上游映射结果
     */
    private String mappingResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogUuid() {
        return logUuid;
    }

    public void setLogUuid(String logUuid) {
        this.logUuid = logUuid;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public String getBillState() {
        return billState;
    }

    public void setBillState(String billState) {
        this.billState = billState;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getMappingResult() {
        return mappingResult;
    }

    public void setMappingResult(String mappingResult) {
        this.mappingResult = mappingResult;
    }

    @Override
    public String toString() {
        return "CallLog{" +
                "id=" + id +
                ", logUuid='" + logUuid + '\'' +
                ", channelType='" + channelType + '\'' +
                ", channelName='" + channelName + '\'' +
                ", head='" + head + '\'' +
                ", body='" + body + '\'' +
                ", result='" + result + '\'' +
                ", del='" + del + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delay=" + delay +
                ", billState='" + billState + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", price=" + price +
                ", mappingResult='" + mappingResult + '\'' +
                '}';
    }
}