package online.goudan.domain;

import lombok.Data;

import java.util.Date;
import java.util.Optional;

/**
 * @author lcl
 * @date 2023/8/22 10:37
 * @desc Father
 */
@Data
public class Father {

    private String name;
    private Date brithday;

    private Optional<Child> child;

}
