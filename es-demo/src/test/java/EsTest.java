import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.beiming.es.Application;
import com.beiming.es.entity.Hit;
import com.beiming.es.mapper.CallLogMapper;
import com.beiming.es.model.CallLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * EsTest
 */
@SpringBootTest(classes = Application.class)
public class EsTest {

    @Resource
    private ElasticsearchTemplate esTemplate;

    @Resource
    private ElasticsearchClient elasticsearchClient;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Test
    public void testDeleteIndex() throws IOException {

        BooleanResponse ss = elasticsearchClient.indices()
                .exists(new ExistsRequest.Builder().index("ss").build());
        if (ss.value()) {
            DeleteIndexResponse response = elasticsearchClient.indices()
                    .delete(DeleteIndexRequest.of(
                            builder -> builder.index("ss")
                    ));
            System.out.println(response.acknowledged());
        }


    }

    @Test
    public void testQueryIndex() throws IOException {
        String indexName = "hit-%s".formatted(LocalDate.now().format(dateTimeFormatter));
        System.out.println(indexName);
        IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);
        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(new Query.Builder().matchPhrase(MatchPhraseQuery.of(builder -> builder.field("name").query("Gpate"))).build())
                .build();
        SearchHits<Hit> searchHits = esTemplate.search(nativeQuery, Hit.class, indexCoordinates);
        searchHits.get()
                .forEach(searchHit -> {
                    System.out.println(searchHit.getContent());
                    Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
                    System.out.println(highlightFields);
                });
    }


    @Resource
    private CallLogMapper callLogMapper;
    Long minId = 0L;

    @Resource
    private ObjectMapper objectMapper;

    @Test
    public void testSave() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String indexName = "call-log-%s".formatted(LocalDate.now().format(dateTimeFormatter));
        IndexOperations indexOperations = esTemplate.indexOps(IndexCoordinates.of(indexName));
        boolean exists = indexOperations.exists();
        if (!exists) {
            indexOperations.create();
        }
        CountDownLatch downLatch = new CountDownLatch(20);
        Lock lock = new ReentrantLock();

        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                lock.lock();
                List<CallLog> list = callLogMapper.selectAllByIdGreaterThan(minId);
                if (!list.isEmpty()) {
                    minId = list.getLast().getId();
                }
                lock.unlock();
                while (!list.isEmpty()) {
                    List<Map<String, Object>> list1 = list.stream()
                            .map(callLog ->
                                    (Map<String, Object>) new HashMap<String, Object>() {
                                        {
                                            put("res_type", "CUSTOMER");
                                            put("log_uuid", callLog.getLogUuid());
                                            put("channel_type", callLog.getChannelType());
                                            put("channel_name", callLog.getChannelName());
                                            put("bill_state", callLog.getBillState());
                                            put("delay", callLog.getDelay());
                                            put("create_time", callLog.getCreateTime().format(formatter));
                                            try {
                                                put("result_content", toObject(callLog.getMappingResult()));
                                            } catch (JsonProcessingException e) {
                                                throw new RuntimeException(e);
                                            }
                                            try {
                                                put("result", toObject(callLog.getResult()));
                                            } catch (JsonProcessingException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }

                            ).toList();
                    esTemplate.save(list1, IndexCoordinates.of(indexName));
                    lock.lock();
                    list = callLogMapper.selectAllByIdGreaterThan(minId);
                    if (!list.isEmpty()) {
                        minId = list.getLast().getId();
                    }
                    lock.unlock();
                    downLatch.countDown();
                }
            });
        }
        downLatch.await();
        executorService.close();
    }

    private Map<String, Object> toObject(String json) throws JsonProcessingException {
        if (StringUtils.isNotBlank(json)) {
            try {
                return objectMapper.readValue(json, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                return new HashMap<String, Object>();
            }
        } else {
            return new HashMap<String, Object>();
        }
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        HttpClient httpClient = HttpClient.newBuilder().build();
//        HttpRequest httpRequest = HttpRequest.newBuilder()
//                .uri(URI.create("http://10.1.1.180:27022/grafana/getToken"))
//                .build();
//        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//        Map<String, Object> data = new ObjectMapper().readValue(response.body(), new TypeReference<>() {
//        });
//        System.out.println(data.get("data"));
//    }

    @Test
    public void testLifecycle() throws InterruptedException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
        String indexPattern = "lcl_test-%s-%s";
        while (true) {
            Thread.sleep(1000L - System.currentTimeMillis() % 1000);
            long start = System.currentTimeMillis();
            Map<String, Object> data = new HashMap<>() {
                {
                    String content = "%s %s %s %s".formatted(RandomStringUtils.randomAlphabetic(3), RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
                    put("content", content);
                    put("create_at", new Date());
                }
            };
//            System.out.println(objectMapper.writeValueAsString(data));

            CreateRequest<Map<String, Object>> request = new CreateRequest.Builder<Map<String, Object>>()
                    .id(UUID.randomUUID().toString().replaceAll("-", ""))
                    .document(data)
                    .index(indexPattern.formatted(formatter.format(LocalDateTime.now()), String.valueOf(LocalDateTime.now().getMinute() / 10)))
                    .build();
            elasticsearchClient.create(request);
            System.out.println(System.currentTimeMillis() - start);
        }
    }
}


