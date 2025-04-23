import lombok.Data;

import java.util.List;

/**
 * @author lcl
 * @date 2024/1/29 14:06
 */
@Data
public class Page<T> {
    private List<T> recordList;
    private Integer count;
}
