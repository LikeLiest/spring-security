package ru.zed.app.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPage {

    @GetMapping("/LinkWorld/error/404")
    public String errorPage() {
        return "error/404";
    }
}
