import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import co.elastic.clients.json.JsonData;
import com.beiming.es.Application;
import com.beiming.es.mapper.StudentRepository;
import com.beiming.es.model.Student;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * StudentEsTest
 */
@SpringBootTest(classes = Application.class)
public class StudentEsTest {

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Test
    public void testSave() throws InterruptedException, IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH-mm");
        int index = 1;
        while (true) {
            List<Student> students = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                students.add(createStu());
//            studentRepository.save(createStu());
            }
//            for (Student student : students) {
//                CreateRequest<Student> createRequest = new CreateRequest.Builder<Student>()
//                        .index("student-" + LocalTime.now().format(dateTimeFormatter))
//                        .document(student)
//                        .id(student.getId())
//                        .build();
//                elasticsearchClient.create(createRequest);
//            }

//            elasticsearchTemplate.save(students, IndexCoordinates.of("student-" + LocalTime.now().format(dateTimeFormatter)));
//            elasticsearchTemplate.save(students, IndexCoordinates.of("stu-1"));

            for (Student student : students) {
                CreateRequest<Student> createRequest = new CreateRequest.Builder<Student>()
                        .index("student-data-stream-lcl")
                        .id(student.getId())
                        .pipeline("add_timestamp")
//                        .pipeline("copy_timestamp")
                        .document(student)
                        .build();
                elasticsearchClient.create(createRequest);
            }
            System.out.println(LocalTime.now().format(dateTimeFormatter));
            Thread.sleep(1000L * 60);
        }
    }


    private Student createStu() {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(RandomStringUtils.randomAlphabetic(10));
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int i = threadLocalRandom.nextInt(3, 5);
        List<String> tags = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            tags.add(RandomStringUtils.randomAlphabetic(3));
        }

        student.setTags(tags);
        student.setDescription("%s %s %s %s".formatted(RandomStringUtils.randomAlphabetic(3), RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2)));
        student.setCreateTime(new Date());
        return student;
    }

}
