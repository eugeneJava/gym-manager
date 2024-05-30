package ua.gym.app;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static java.util.Collections.emptyList;

public class HttpRequestTokenAuthenticationFilter extends GenericFilterBean
        implements ApplicationEventPublisherAware {

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    protected ApplicationEventPublisher eventPublisher;

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private RememberMeServices rememberMeServices = new NullRememberMeServices();


    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    private final HttpRequestTokenStorage tokenStorage;
    private final UserDetailsService userDetailsService;

    public HttpRequestTokenAuthenticationFilter(
            HttpRequestTokenStorage tokenStorage,
            UserDetailsService userDetailsService) {
        this.tokenStorage = tokenStorage;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getParameter("token");
        if (token != null && tokenStorage.isValid(token)) {
            String userParam = request.getParameter("user");
            if (userParam == null || userParam.isEmpty()) {
               throw new BadCredentialsException("Invalid request params");
            }

            UserDetails user = userDetailsService.loadUserByUsername(userParam);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), emptyList());
            successfulAuthentication((HttpServletRequest) request, (HttpServletResponse) response, chain, authentication);
        }

        chain.doFilter(request, response);

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authResult);
        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
        }
        this.rememberMeServices.loginSuccess(request, response, authResult);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
    }


    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }



    public void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }
}
