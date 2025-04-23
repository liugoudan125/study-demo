package online.goudan.service.impl;

import online.goudan.service.Order;
import org.springframework.stereotype.Component;

/**
 * @author lcl
 * @date 2023/8/16 9:49
 * @desc OrderFirst
 */
@Component
@org.springframework.core.annotation.Order(566)
public class OrderFirst implements Order {

    @Override
    public void say() {
        System.out.println("first");
    }
}
