package org.example.domain;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.*;

/**
 * @author ${AUTHOR}
 * @date 2023/3/20 23:02
 * @desc ${DESC}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Test {
    private Long id;

    private String name;

    private Integer age;

    private Date brithday;
}