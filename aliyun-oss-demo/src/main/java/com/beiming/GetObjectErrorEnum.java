package com.beiming;

/**
 * GetObjectErrorEnum
 * 阿里云oss getObject api 错误码枚举
 */
public enum GetObjectErrorEnum {
    NO_SUCH_KEY("NoSuchKey", "目标Object不存在。"),
    SYMLINK_TARGET_NOT_EXIST("SymlinkTargetNotExist", "Object类型为软链接，且目标Object不存在。"),
    INVALID_TARGET_TYPE("", """
                下载归档类型的Object时：
                没有提交RestoreObject请求或者上一次提交RestoreObject已经超时。
                已经提交RestoreObject请求，但数据的RestoreObject操作还没有完成。
            """),
    NOT_MODIFIED("Not Modified", """
                返回该错误的可能原因如下：
                指定了If-Modified-Since请求头，但请求的Object在指定的时间后没被修改过。
                指定了If-None-Match请求头，且请求的Object的ETag值和您提供的ETag相等。
            """),
    PRECONDITION_FAILED("Precondition Failed", """
                返回该错误的可能原因如下：
                指定了If-Unmodified-Since，但指定的时间早于Object实际修改时间 。
                指定了If-Match，但请求的Object的ETag值和您提供的ETag值不相等。
            """),
    NOT_FOUND("Not Found", "在请求中未指定Object的versionId，且Object的当前版本是删除标记（Delete Marker）时，返回该错误。"),
    METHOD_NOT_ALLOWED("Method Not Allowed", "在请求中指定了Object的versionId，且versionId对应的是删除标记时，返回该错误。");


    private final String errorCode;
    private final String explain;

    GetObjectErrorEnum(String errorCode, String explain) {
        this.errorCode = errorCode;
        this.explain = explain;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getExplain() {
        return explain;
    }

    public static GetObjectErrorEnum getByErrorCode(String errorCode) {
        for (GetObjectErrorEnum e : GetObjectErrorEnum.values()) {
            if (e.getErrorCode().equals(errorCode)) {
                return e;
            }
        }
        return null;
    }
}
