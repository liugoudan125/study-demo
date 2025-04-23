import lombok.Data;

import java.util.List;

/**
 * @author lcl
 * @date 2024/1/29 13:57
 */
@Data
public class Album {

    private int id;

    private String albumName;

    private String albumDesc;

    private String albumCover;

    private List<Tag> tagNameList;

    private int count;

    private String thumbnailUrl;


    @Data
    public static class Tag {


        private int id;

        private int parentId;

        private String tagName;

        private String createTime;

        private String updateTime;

        private String children;
    }
}
