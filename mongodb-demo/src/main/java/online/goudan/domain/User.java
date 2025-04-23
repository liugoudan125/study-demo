package online.goudan.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author goudan
 * @date 2023/7/26 17:19
 * @desc User
 */

@Document
@Data
public class User {

    private String name;
    private Integer age;
    private String sex;
}
