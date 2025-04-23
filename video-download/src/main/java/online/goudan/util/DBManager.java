package online.goudan.util;

import online.goudan.domain.M3U8;
import online.goudan.domain.M3U8Ts;
import online.goudan.domain.VideoInfo;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @author 刘成龙
 * @date 2021/6/24 13:50
 * @desc DBManager
 */
public class DBManager {
    private static DBManager instance = new DBManager();

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DBManager() {

    }

    public static DBManager getInstance() {
        return instance;
    }

    public void saveBase64Name(VideoInfo videoInfo) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql:///video-download?useSSL=false", "root", "root")) {
            String fn = Base64.getEncoder().encodeToString(videoInfo.getVideoName().getBytes(StandardCharsets.UTF_8));

            String querySql = "select id from au where name = ?";
            PreparedStatement queryStatement = connection.prepareStatement(querySql);
            queryStatement.setString(1, fn);
            ResultSet resultSet = queryStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("数据库中已经存在，"+resultSet.getInt("id"));
                return;
            }
            String sql = "insert into au(name,url,gmt_create,gmt_modified) values(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fn);
            preparedStatement.setString(2, videoInfo.getM3u8Url());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = format.format(new Date());
            preparedStatement.setString(3, dateStr);
            preparedStatement.setString(4, dateStr);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                ResultSet maxResult = connection.prepareStatement("select MAX(id) FROM au").executeQuery();
                if (maxResult.next()) {
                    System.out.println("id: " + maxResult.getInt("MAX(id)"));
                }
            } else {
                System.out.println(videoInfo.getVideoName() + ", 保存到数据库失败");
            }
        } catch (Exception e) {
            System.out.println(videoInfo.getVideoName() + ", 保存到数据库失败，" + e.getMessage());
        }
    }

    public List<VideoInfo> getm3U8List(int startId, int endId) {
        List<VideoInfo> videoInfos = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql:///novel?useSSL=false", "root", "root")) {
            String querySql = "select id,name,url from au where id between ? and ?";
            PreparedStatement statement = connection.prepareStatement(querySql);
            statement.setInt(1, startId);
            statement.setInt(2, endId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                byte[] names = Base64.getDecoder().decode(resultSet.getString("name"));
                VideoInfo videoInfo = new VideoInfo(resultSet.getInt("id"), new String(names, StandardCharsets.UTF_8), resultSet.getString("url"));
                videoInfos.add(videoInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoInfos;
    }
}
