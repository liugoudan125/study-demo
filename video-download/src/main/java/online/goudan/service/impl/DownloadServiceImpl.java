package online.goudan.service.impl;

import online.goudan.domain.M3U8;
import online.goudan.domain.Video;
import online.goudan.service.DownloadService;
import online.goudan.util.M3U8Manager;
import online.goudan.util.ProcessManager;
import online.goudan.util.listener.M3U8TsListener;
import online.goudan.util.ProcessMap;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

/**
 * @author 刘成龙
 * @description
 * @date 2021/8/3 21:41
 */
@Service
public class DownloadServiceImpl implements DownloadService {
    @Override
    public void downloadVideo(Video video, String dir) {
//        ProcessManager processManager = ProcessManager.createProcessManager(dir, video.getId() + "_" + video.getName());
//        Optional<ProcessManager> optionalProcessManager = Optional.ofNullable(processManager);
        M3U8Manager m3U8Manager = M3U8Manager.getInstance();
        m3U8Manager.setListener(new M3U8TsListener() {
            @Override
            public void onStartDownload() {
                ProcessMap.changeDownloading(video.getName());
            }

            @Override
            public void onDownloading(int process, String detail) {
                ProcessMap.downloading(video.getName(), process, detail);
//                optionalProcessManager.ifPresent(manager -> manager.setProcess(process));
            }

            @Override
            public void onFinish(M3U8 m3U8) {
//                optionalProcessManager.ifPresent(manager -> {
//                    manager.setProcess(m3U8.getProcess());
//                    manager.addMessage("ts 下载完成了,开始合并");
//                });
                m3U8Manager.mergeM3U8Ts();
            }

            @Override
            public void mergerProcess(File file) {
//                optionalProcessManager.ifPresent(manger -> manger.addMessage("合并了" + file.getName()));
            }

            @Override
            public void megerFinish(File parentFile) {
                ProcessMap.changeDownloaded(video.getName());

//                optionalProcessManager.ifPresent(manger -> {
//                    manger.addMessage("合并完成");
//                    manger.close();
//                });
                if (parentFile.listFiles().length == 0) {
                    parentFile.delete();
                } else {
                    File[] files = parentFile.listFiles();
                    for (File file : files) {
                        file.delete();
                    }
                    parentFile.delete();
                }

            }

            @Override
            public void onDownloadException(Exception exception) {
//                optionalProcessManager.ifPresent(manager -> {
//                    manager.addMessage(exception.getMessage());
//                });
            }
        });

        m3U8Manager.setVideo(video);
        m3U8Manager.generateM3U8(dir);
        m3U8Manager.downloadM3U8Ts();
    }
}
