package online.goudan.test;

import com.mongodb.client.result.UpdateResult;
import online.goudan.domain.User;
import online.goudan.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

/**
 * @author
 * @date 2023/7/26 17:23
 * @desc UserTest
 */
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testFind() {
//        List<User> userList = userRepository.findByName("Russell Cruz");
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("name").is("Wong Fat");
//        criteria.and("age").is(76);
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Direction.ASC, "age"));
        long total = mongoTemplate.count(query, User.class);
//        Pageable pageable = PageRequest.of(3, 5);
//        query.with(pageable);
        List<User> userList = mongoTemplate.find(query, User.class);

        System.out.println(total);
        userList.forEach(System.out::println);

    }

    @Test
    public void testIUD() {
        Criteria criteria = new Criteria();
        criteria.and("name").is("Wong Fat");
        criteria.and("age").is(76);
        Query query = new Query(criteria);

        Update update = new Update();
        update.set("sex","F");
//        update.unset("name").unset("age"); //unset 会将字段设置为null
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, User.class);
        System.out.println(updateResult.getMatchedCount());
        System.out.println(updateResult.getMatchedCount());
    }
}
