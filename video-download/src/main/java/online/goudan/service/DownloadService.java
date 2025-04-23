package online.goudan.service;

import online.goudan.domain.Video;

/**
 * @author 刘成龙
 * @description
 * @date 2021/8/3 21:41
 */
public interface DownloadService {
    void downloadVideo(Video video,String dir);
}
