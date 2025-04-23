package online.goudan.domain;

import java.time.LocalDateTime;

/**
 *
 * @author ${AUTHOR}
 * @date 2023/4/5 0:40
 * @desc ${DESC}  */
public class SystemConfigDO {
    private Integer id;

    private String propertyName;

    private String propertyValue;

    private LocalDateTime creeateTime;

    private LocalDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public LocalDateTime getCreeateTime() {
        return creeateTime;
    }

    public void setCreeateTime(LocalDateTime creeateTime) {
        this.creeateTime = creeateTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}