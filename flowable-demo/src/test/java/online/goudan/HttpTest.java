package online.goudan;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @date 2023/5/31 14:40
 * @desc HttpTest
 */
@SpringBootTest
public class HttpTest {

    @Autowired
    private RestTemplate aaa;


    @Test
    public void testPost() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("aaa", 124);
        params.add("bbb", "joda");
        params.add("ccc", "joss");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> responseEntity = aaa.postForEntity("http://goudan.online", httpEntity, String.class);
        System.out.println(responseEntity.getBody());
    }
}




