package ua.gym.app;

import org.springframework.stereotype.Component;
import ua.gym.utils.Assertions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class HttpRequestTokenStorage {
    private final Set<TimedToken> tokenStorage = Collections.synchronizedSet(new HashSet<>());

    public void setToken(String token) {
        tokenStorage.add(new TimedToken(token));
    }

    public boolean isValid(String token) {
        return tokenStorage.stream()
                .filter(t -> t.getToken().equals(token))
                .anyMatch(t -> t.creationTime.plusMinutes(10).isAfter(LocalDateTime.now()));
    }

    private static class TimedToken {
        private final String token;
        private final LocalDateTime creationTime;

        private TimedToken(String token) {
            Assertions.assertPresent(token);
            this.token = token;
            this.creationTime = LocalDateTime.now();
        }

        private String getToken() {
            return token;
        }
    }
}
