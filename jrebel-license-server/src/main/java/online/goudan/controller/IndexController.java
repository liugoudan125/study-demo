package online.goudan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author
 * @date 2023/4/19 10:57
 * @desc IndexController
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        int port = request.getServerPort();
        String html = "<h1>Hello,This is a Jrebel & JetBrains License Server!</h1>";
        html += "<p>License Server started at http://localhost:" + port;
        html += "<p>JetBrains Activation address was: <span style='color:red'>http://localhost:" + port + "/";
        html += "<p>JRebel 7.1 and earlier version Activation address was: <span style='color:red'>http://localhost:" + port + "/{tokenname}</span>, with any email.";
        html += "<p>JRebel 2018.1 and later version Activation address was: http://localhost:" + port + "/{guid}(eg:<span style='color:red'>http://localhost:" + port + "/" + UUID.randomUUID().toString() + "</span>), with any email.";

        response.getWriter().println(html);
    }
}
