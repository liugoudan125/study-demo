import lombok.Data;

/**
 * @author lcl
 * @date 2024/1/29 14:03
 */
@Data
public class R<T> {

    private int code;
    private Boolean flag;

    private String message;
    private T data;

}
