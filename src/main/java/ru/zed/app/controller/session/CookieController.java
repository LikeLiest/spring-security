package ru.zed.app.controller.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/LinkWorld/cookie")
public class CookieController {

    @GetMapping("/cookie")
    public String getCookie(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            List<CookieWrapper> cookieWrappers = Arrays.stream(cookies)
                    .map(CookieWrapper::new)
                    .toList();
            model.addAttribute("cookies", cookieWrappers);
        }
        return "cookie/cookie";
    }

//    @GetMapping("/getCookie")
//    public String getCookie(@CookieValue(value = "username", defaultValue = "Ivan") String username, Principal principal, HttpServletRequest request, Model model) {
//        Cookie cookie = new Cookie("username", principal.getName());
//
//        model.addAttribute("username", cookie.getValue());
//        return "cookie/getCookie";
//    }

    @GetMapping("/getCookie")
    public String getCookie(@CookieValue(name = "username", defaultValue = "Ivan") String username, Model model) {
        model.addAttribute("username", username);
        return "cookie/getCookie";
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Arrays.stream(cookies).forEach(cookie -> {
                cookie.setValue("");
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            });
            return ResponseEntity.ok("Куки очищены");
        }
        return ResponseEntity.badRequest().body("Куки не очищены");
    }
}
