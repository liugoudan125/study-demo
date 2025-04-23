package online.goudan.util.listener;

import online.goudan.domain.M3U8;

import java.io.File;

/**
 * @author 刘成龙
 * @date 2021/6/24 13:42
 * @desc M3U8TsListener
 */
public interface M3U8TsListener {

    void onStartDownload();

    void onDownloading(int process,String detail);

    void onFinish(M3U8 m3U8);

    void mergerProcess(File file);

    void megerFinish(File parentFile);

    void onDownloadException(Exception exception);
}
