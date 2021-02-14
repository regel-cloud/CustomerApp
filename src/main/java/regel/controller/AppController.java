package regel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {
    private static final String MAIN_PAGE = "mainpage";

    @GetMapping
    public String showMainPge() {

        return MAIN_PAGE;
    }
}
