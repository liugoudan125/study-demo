package online.goudan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 刘成龙
 * @description
 * @date 2021/8/7 15:33
 */
@Controller
public class PageController {

    @GetMapping("/{page}")
    public String page(@PathVariable("page") String page) {
        return page;
    }

    @GetMapping("")
    public String index() {
        return "index";
    }
}
