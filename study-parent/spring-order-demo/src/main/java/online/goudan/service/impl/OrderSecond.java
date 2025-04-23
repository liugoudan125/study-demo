package online.goudan.service.impl;

import online.goudan.service.Order;
import org.springframework.stereotype.Component;

/**
 * @author lcl
 * @date 2023/8/16 9:49
 * @desc OrderSecond
 */
@Component
@org.springframework.core.annotation.Order(2)
public class OrderSecond implements Order {

    @Override
    public void say() {
        System.out.println("second");
    }
}
