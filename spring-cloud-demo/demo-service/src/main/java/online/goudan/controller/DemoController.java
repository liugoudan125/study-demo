package online.goudan.controller;

import jakarta.annotation.Resource;
import online.goudan.properties.WhitelistProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lcl
 * @date 2023/8/21 11:21
 * @desc DemoController
 */
@RestController
@RequestMapping("demo")
@RefreshScope
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);
    @Value("${my.name:lcl}")
    private String name;

    @Resource
    private WhitelistProperties whitelistProperties;

    @GetMapping("hello")
    public WhitelistProperties hello() {
        log.info("name: {}", name);
        return whitelistProperties;
    }
}
