package ua.gym.app;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.net.InetAddress;

public class InternalHttpCommunicationFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        InetAddress inetAddress = InetAddress.getLocalHost();
        String myIp = inetAddress.getHostAddress();

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String remoteIp = httpRequest.getRemoteAddr();

        String myIpSubnet = getSubnet(myIp);
        String remoteIpSubnet = getSubnet(remoteIp);
        if (myIpSubnet.equals(remoteIpSubnet)) {
            chain.doFilter(request, response);
        } else {
           throw new AccessDeniedException("Access denied");
        }
    }

    private String getSubnet(String ip) {
        String[] ipAddresses = ip.split("\\.");
        return ipAddresses[0] + "." + ipAddresses[1];
    }

}
