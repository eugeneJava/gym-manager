package ua.gym.ui.internal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.gym.app.HttpRequestTokenStorage;

import java.util.UUID;

@RestController
@RequestMapping("/internal-api")
public class TokenWebService {
    private final HttpRequestTokenStorage tokenStorage;

    public TokenWebService(HttpRequestTokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
    }

    @RequestMapping("/token")
    public String getToken() {
        String token = UUID.randomUUID().toString();
        tokenStorage.setToken(token);
        return token;
    }
}
