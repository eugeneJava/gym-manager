package ua.gym.app;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class TokenAuthentication extends AbstractAuthenticationToken {
    private String token;

    public TokenAuthentication(String token) {
        super(Collections.emptyList());
        this.token = token;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public String getPrincipal() {
        return "N/A";
    }
}
