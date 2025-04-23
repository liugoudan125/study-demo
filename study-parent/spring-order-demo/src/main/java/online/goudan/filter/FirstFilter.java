package online.goudan.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author lcl
 * @date 2023/8/14 17:28
 * @desc 第一个过滤器
 */
@WebFilter(urlPatterns = "/", filterName = "firstFilter")
@Component
@Order(15)
public class FirstFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("第一个过滤器before");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("第一个过滤器after");

    }
}
