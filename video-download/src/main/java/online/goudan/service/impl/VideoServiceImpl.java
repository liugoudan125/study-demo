package online.goudan.service.impl;

import com.github.pagehelper.PageHelper;
import online.goudan.domain.Video;
import online.goudan.mapper.VideoMapper;
import online.goudan.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 刘成龙
 * @description
 * @date 2021/8/3 20:47
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Video getVideoById(int id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Video> getList(int page, int pageSize, Boolean ace) {
        String orderBy = "id " + (ace ? "" : "desc");
        PageHelper.startPage(page, pageSize, orderBy);
        return videoMapper.selectList();
    }


    @Override
    public boolean saveVideo(Video video) {
        try {
            videoMapper.insert(video);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateVideo(Video video) {
        try {
            videoMapper.updateByPrimaryKeySelective(video);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteVideo(int id) {
        try {
            videoMapper.deleteByPrimaryKey(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Video getVideoByPageUrl(String pageUrl) {
        return videoMapper.selectByPageUrl(pageUrl);
    }

    @Override
    public Video getVideoByName(String name) {
        return videoMapper.selectByName(name);
    }
}
