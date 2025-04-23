package online.goudan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * @author lcl
 * @date 2023/8/24 17:09
 * @desc UserController
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("info")
    public Principal info(Principal principal, HttpSession session) {
        System.out.println(session.getId());

        return principal;
    }
}
