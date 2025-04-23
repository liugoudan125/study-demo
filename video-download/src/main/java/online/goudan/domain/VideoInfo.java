package online.goudan.domain;

/**
 * @author 刘成龙
 * @date 2021/6/24 11:29
 * @desc VideoInfo
 */
public class VideoInfo {
    private Integer id;
    private String videoName;
    private String m3u8Url;


    public VideoInfo(String videoName, String m3u8Url) {
        this.videoName = videoName;
        this.m3u8Url = m3u8Url;
    }

    public VideoInfo(Integer id, String videoName, String m3u8Url) {
        this.id = id;
        this.videoName = videoName;
        this.m3u8Url = m3u8Url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getM3u8Url() {
        return m3u8Url;
    }

    public void setM3u8Url(String m3u8Url) {
        this.m3u8Url = m3u8Url;
    }

    @Override
    public String toString() {
        return videoName + " (" + m3u8Url + ")";
    }
}
