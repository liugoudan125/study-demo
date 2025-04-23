package online.goudan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.servlet.Filter;
import java.util.Map;

@SpringBootApplication
public class Client01Application {
    public static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(Client01Application.class, args);
        Map<String, Filter> beansOfType = context.getBeansOfType(Filter.class);
        System.out.println(beansOfType);
    }
}
