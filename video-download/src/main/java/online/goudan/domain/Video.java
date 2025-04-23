package online.goudan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Data
public class Video {
    private Integer id;

    private String name;

    private String url;

    private Date gmtCreate;

    private Date gmtModified;

    private String pageUrl;
    @JsonIgnore
    private boolean enableDecode;

    private boolean downloadStatus;

    public String getName() {
        if (enableDecode) {
            return new String(Base64.getDecoder().decode(name), StandardCharsets.UTF_8);
        }
        return name;
    }

    public String getGmtCreate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gmtCreate);
    }

    public String getGmtModified() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gmtCreate);
    }
}