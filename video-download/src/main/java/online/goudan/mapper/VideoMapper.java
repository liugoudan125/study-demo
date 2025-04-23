package online.goudan.mapper;

import online.goudan.domain.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);


    List<Video> selectList();

    Video selectByPageUrl(String pageUrl);

    Video selectByName(String name);
}