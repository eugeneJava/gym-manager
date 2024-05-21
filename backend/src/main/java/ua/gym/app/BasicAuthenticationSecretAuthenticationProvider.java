package ua.gym.app;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;
import java.util.Set;

public class BasicAuthenticationSecretAuthenticationProvider implements AuthenticationProvider {

    private static String USER_NAME = "admin";
    private final Secrets secrets;

    public BasicAuthenticationSecretAuthenticationProvider(Secrets secrets) {
        this.secrets = secrets;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        String secret = secrets.getBasicAuthSecret();
        if (USER_NAME.equals(username) && secret.equals(password)) {
            return new UsernamePasswordAuthenticationToken("admin", password, Collections.emptyList());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
