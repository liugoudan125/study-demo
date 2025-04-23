package online.goudan.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘成龙
 * @date 2021/6/24 10:40
 * @desc M3U8
 */
public class M3U8 {
    private String basePath;
    private String localDirPath;
    private float totalTime;
    private float downloadedTime;
    private List<M3U8Ts> m3U8TsList = new ArrayList<>();
    private List<File> m3U8TsFileList = new ArrayList<>();

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getLocalDirPath() {
        return localDirPath;
    }


    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public void setLocalDirPath(String localDirPath) {
        this.localDirPath = localDirPath;
    }

    public List<M3U8Ts> getM3U8TsList() {
        return m3U8TsList;
    }


    public List<File> getM3U8TsFileList() {
        return m3U8TsFileList;
    }


    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addM3U8Ts(M3U8Ts m3U8Ts) {
        m3U8TsList.add(m3U8Ts);
    }

    public void addM3U8TsFile(File file) {
        m3U8TsFileList.add(file);
    }

    private int downloadCount;

    public synchronized void addDownloadedM3U8Ts(M3U8Ts m3U8Ts) {
        downloadedTime += m3U8Ts.getSeconds();
        downloadCount++;
    }

    public int getProcess() {
        return (int) (downloadedTime * 100 / totalTime);
    }

    public String getDownloadDetail() {
        return downloadCount + "/" + m3U8TsList.size();
    }
}
