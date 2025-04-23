package online.goudan.request;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lcl
 * @date 2023/8/11 14:31
 * @desc ApiVersionCondition
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return this;
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        return this;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        return 1;
    }
}
