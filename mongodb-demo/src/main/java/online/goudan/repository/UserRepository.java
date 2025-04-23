package online.goudan.repository;

import online.goudan.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author goudan
 * @date 2023/7/26 17:22
 * @desc UserRepository
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {

    List<User> findByName(String name);
}
