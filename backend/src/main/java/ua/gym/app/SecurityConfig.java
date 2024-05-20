package ua.gym.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }


    @Bean
    public SecurityFilterChain filterChainForInternalRequests(HttpSecurity http) throws Exception {
        http.securityMatcher()
                .authorizeRequests(
                        auth -> auth.requestMatchers("/internal-api/**").authenticated()).;
        return http.build();
    }

}