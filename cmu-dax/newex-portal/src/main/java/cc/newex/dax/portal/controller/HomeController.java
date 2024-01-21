package cc.newex.dax.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
@Controller
@RequestMapping("")
public class HomeController {
    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/**")
    public String all(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        return "index";
    }
}
