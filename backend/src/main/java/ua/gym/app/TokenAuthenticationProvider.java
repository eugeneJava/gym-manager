package ua.gym.app;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

public class TokenAuthenticationProvider implements AuthenticationProvider {
    private final HttpRequestTokenStorage tokenService;

    public TokenAuthenticationProvider(HttpRequestTokenStorage tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }

    @Override
    public TokenAuthentication authenticate(Authentication authentication) {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        String token = tokenAuthentication.getCredentials();

        tokenService.isValid(token);
        return new TokenAuthentication(token);
    }
}
