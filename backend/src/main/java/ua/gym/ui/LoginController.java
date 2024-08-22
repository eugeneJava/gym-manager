package ua.gym.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/myLogin")
    public String myLogin() {
        return "redirect:/frontend/index.html";
    }
}
