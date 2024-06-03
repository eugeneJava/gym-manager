package ua.gym.app;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Secrets secrets;
    private final HttpRequestTokenStorage tokenStorage;

    public SecurityConfig(Secrets secrets, HttpRequestTokenStorage tokenStorage) {
        this.secrets = secrets;
        this.tokenStorage = tokenStorage;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainForInternalRequests(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
                .securityMatcher("/internal-api/**")
                .authorizeRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterAfter(new InternalHttpCommunicationFilter(), ConcurrentSessionFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth.anyRequest().authenticated())
                .addFilterBefore(
                        createHttpRequestTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

   /* @Bean
    @Profile("dev")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }*/

    private HttpRequestTokenAuthenticationFilter createHttpRequestTokenFilter() {
        HttpRequestTokenAuthenticationFilter httpRequestTokenAuthenticationFilter =
                new HttpRequestTokenAuthenticationFilter(tokenStorage, userDetailService(secrets));
        httpRequestTokenAuthenticationFilter.setSecurityContextRepository(new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        ));

        return httpRequestTokenAuthenticationFilter;
    }

    @Bean
    public UserDetailsService userDetailService(Secrets secrets) {
        UserDetails webUser1 = User.builder()
                .username("Evgeniy")
                .password(secrets.getFormLoginPassword())
                .build();

        UserDetails webUser2 = User.builder()
                .username("Alina")
                .password(secrets.getFormLoginPassword())
                .build();

        UserDetails internalHttpCommunicationUser = User.builder()
                .username("admin")
                .password(secrets.getBasicAuthPassword())
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(webUser1, webUser2, internalHttpCommunicationUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}