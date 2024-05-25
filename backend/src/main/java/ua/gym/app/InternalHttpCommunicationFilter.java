package ua.gym.app;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.util.Set;

public class InternalHttpCommunicationFilter implements Filter {

    private Set<String> allowedIps = Set.of("127.0.0.1");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String remoteIp = httpRequest.getRemoteAddr();

        if (allowedIps.contains(remoteIp)) {
            chain.doFilter(request, response);
        } else {
           throw new AccessDeniedException("Access denied");
        }
    }
}
