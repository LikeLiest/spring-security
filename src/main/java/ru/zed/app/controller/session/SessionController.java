package ru.zed.app.controller.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zed.app.Model.entity.User.UserEntity;

@Controller
@RequestMapping("/LinkWorld/session")
public class SessionController {

    @GetMapping("/session")
    public String sessionInfo(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "session/session";
    }
}
