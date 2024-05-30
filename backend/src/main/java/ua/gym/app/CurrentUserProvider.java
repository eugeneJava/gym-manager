package ua.gym.app;

import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserProvider {
    public static String getCurrentUserName() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return username;
    }
}
