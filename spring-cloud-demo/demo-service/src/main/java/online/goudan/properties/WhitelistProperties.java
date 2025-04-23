package online.goudan.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WhitelistProperties
 */
@ConfigurationProperties(prefix = "task.list.white")
@Component
@Getter
@Setter
public class WhitelistProperties {
    private Boolean enable;

    private List<Group> groups;

    @Getter
    @Setter
    public static class Group {
        /**
         * 是否全局 0,全局 1 客户
         */
        private Integer type;

        /**
         * 客户Id
         */
        private Long customerId;

        private String[] list;
    }
}
