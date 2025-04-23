import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.List;

/**
 * @author lcl
 * @date 2024/1/29 13:37
 */
public class Main {


    public static void main(String[] args) throws Exception {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Request rootRequest = new Request.Builder()
                .url("https://www.xiaoger.top/api/photos/albums")
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36")
                .post(RequestBody.create("{\"current\":1,\"keywords\":\"\",\"sort\":\"\",\"tagIdList\":[],\"size\":100}".getBytes()))
                .build();

        Response response = httpClient.newCall(rootRequest).execute();
        String string = response.body().string();
        R<Page<Album>> r = JSON.parseObject(string, new TypeReference<R<Page<Album>>>() {
        }.getType());
        List<Album> recordList = r.getData().getRecordList();
        for (Album album : recordList) {
            System.out.println(album);
        }

    }
}
