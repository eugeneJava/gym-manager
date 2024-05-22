package ua.gym.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Secrets secrets;

    public SecurityConfig(Secrets secrets) {
        this.secrets = secrets;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainForInternalRequests(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
                .securityMatcher("/internal-api/**")
                .authorizeRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService users(Secrets secrets) {
        UserDetails webUser = User.builder()
                .username("user")
                .password(secrets.getFormLoginPassword())
                .build();

        UserDetails internalHttpCommunicationUser = User.builder()
                .username("admin")
                .password(secrets.getBasicAuthPassword())
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(webUser, internalHttpCommunicationUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}