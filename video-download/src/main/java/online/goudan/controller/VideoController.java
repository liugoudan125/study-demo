package online.goudan.controller;

import com.github.pagehelper.PageInfo;
import online.goudan.domain.SystemConfigDO;
import online.goudan.domain.Video;
import online.goudan.domain.VideoInfo;
import online.goudan.mapper.SystemConfigMapper;
import online.goudan.mapper.VideoMapper;
import online.goudan.service.DownloadService;
import online.goudan.service.VideoService;
import online.goudan.util.DBManager;
import online.goudan.util.ProcessMap;
import online.goudan.util.WebDriverManager;
import org.apache.tomcat.jni.Mmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 刘成龙
 * @description
 * @date 2021/8/3 20:38
 */
@RestController
@RequestMapping("video")
@SuppressWarnings("all")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private DownloadService downloadService;


    @Autowired
    private SystemConfigMapper systemConfigMapper;

    private boolean enableDecoed;
    private ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

    @GetMapping("")
    public PageInfo<Video> getVideoList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                                        @RequestParam(value = "ace", defaultValue = "true") Boolean ace) {

        List<Video> videoList = videoService.getList(page, pageSize, ace);
        for (Video video : videoList) {
            video.setEnableDecode(enableDecoed);
            video.setDownloadStatus(set.contains(video.getId()));
        }
        PageInfo<Video> pageInfo = PageInfo.of(videoList);
        return pageInfo;
    }

    @PostMapping("/driver")
    public boolean webDriver(String pageUrl) {
        new Thread(() -> {
            Video video = videoService.getVideoByPageUrl(pageUrl);
            if (video == null) {
                WebDriverManager driverManager = WebDriverManager.getInstance();
                VideoInfo videoInfo = driverManager.useWebDriverGetVideoInfo(pageUrl);

                String name = Base64.getEncoder().encodeToString(videoInfo.getVideoName().getBytes(StandardCharsets.UTF_8));
                video = videoService.getVideoByName(name);
                if (video == null) {
                    video = new Video();
                    video.setName(name);
                    video.setUrl(videoInfo.getM3u8Url());
                    video.setPageUrl(pageUrl);
                    video.setGmtCreate(new Date());
                    video.setGmtModified(new Date());
                    videoService.saveVideo(video);
                } else {
                    video.setPageUrl(pageUrl);
                    video.setGmtModified(new Date());
                    videoService.updateVideo(video);
                }
            }
        }).start();
        return true;
    }

    private Set<Integer> set = new HashSet<>();

    @GetMapping("download")
    public String downloadVideo(Integer id) {
        Video video = videoService.getVideoById(id);
        video.setEnableDecode(true);
        ProcessMap.addMap(video.getName());
        executorService.execute(() -> {
            if (!ProcessMap.existDownload(video.getName())) {
                downloadService.downloadVideo(video, systemConfigMapper.selectByPropertyName(DOWNLOAD_PATH).getPropertyValue());
            }
        });
        if (set.contains(id)) {
            return "已经在下载列表中";
        }
        set.add(id);
        return "开始下载：" + video.getName();
    }

    @GetMapping("modify/downloadSize/{size}")
    public Boolean modifySize(@PathVariable("size") Integer size) {
        executorService.setMaximumPoolSize(size);
        executorService.setCorePoolSize(size);
        return true;
    }


    @GetMapping("status")
    public Map<String, List<Map<String, Object>>> downloadStatus() {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Map<String, Object>> waitList = getList(ProcessMap.getWaitDownloadMap());
        result.put("wait", waitList);
        result.put("downloading", getList(ProcessMap.getDownloadingMap()));
        List<Map<String, Object>> downloadedList = getList(ProcessMap.getDownloadedMap());
        result.put("downloaded", downloadedList);
        return result;
    }


    private List<Map<String, Object>> getList(Map<String, ProcessMap.ProcessInfo> processMap) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (String s : processMap.keySet()) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("name", s);
            map.put("process", processMap.get(s).getProcess());
            map.put("detail", processMap.get(s).getDetail());
            list.add(map);
        }
        return list;
    }

    @GetMapping("/decode/{enable}")
    public void decodeName(@PathVariable("enable") Boolean enable) {
        this.enableDecoed = enable;
    }

    @GetMapping("/decode")
    public boolean getEncodeStatus() {
        return this.enableDecoed;
    }

    @PostMapping("/path")
    public boolean decodeName(@RequestParam(value = "dir", defaultValue = "au") String dir) {
        SystemConfigDO systemConfigDO = systemConfigMapper.selectByPropertyName(DOWNLOAD_PATH);
        systemConfigDO.setPropertyValue(dir);
        systemConfigMapper.updateByPrimaryKeySelective(systemConfigDO);
        return true;
    }

    public static final String DOWNLOAD_PATH = "download_path";

    @GetMapping("/getPath")
    public String getPath() {
        SystemConfigDO systemConfigDO = systemConfigMapper.selectByPropertyName(DOWNLOAD_PATH);
        return systemConfigDO.getPropertyValue();
    }

}
