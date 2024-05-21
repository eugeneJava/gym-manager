package ua.gym.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource("secrets.properties")
public class Secrets {
    @Value("${http.internal.communication.allowed.ids}")
    private Set<String> internalHttpCommunicationAllowedIds;

    @Value("${http.internal.communication.basic.auth.secret}")
    private String basicAuthSecret;

    public Set<String> getInternalHttpCommunicationAllowedIds() {
        return Collections.unmodifiableSet(internalHttpCommunicationAllowedIds);
    }

    public void setInternalHttpCommunicationAllowedIds(Set<String> internalHttpCommunicationAllowedIds) {
        this.internalHttpCommunicationAllowedIds = new HashSet<>(internalHttpCommunicationAllowedIds);
    }

    public String getBasicAuthSecret() {
        return basicAuthSecret;
    }

    public void setBasicAuthSecret(String basicAuthSecret) {
        this.basicAuthSecret = basicAuthSecret;
    }
}
