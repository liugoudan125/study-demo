package online.goudan.service;

import online.goudan.domain.Video;

import java.util.List;

/**
 * @author 刘成龙
 * @description
 * @date 2021/8/3 20:47
 */
public interface VideoService {

    Video getVideoById(int id);

    List<Video> getList(int page, int pageSize, Boolean ace);

    boolean saveVideo(Video video);

    boolean updateVideo(Video video);

    boolean deleteVideo(int id);

    Video getVideoByPageUrl(String pageUrl);

    Video getVideoByName(String name);
}
