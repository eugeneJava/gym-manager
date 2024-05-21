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
    private Set<String> internalHttpCommunicationAllowedIds = new HashSet<>();

    public Set<String> getInternalHttpCommunicationAllowedIds() {
        return Collections.unmodifiableSet(internalHttpCommunicationAllowedIds);
    }

    public void setInternalHttpCommunicationAllowedIds(Set<String> internalHttpCommunicationAllowedIds) {
        this.internalHttpCommunicationAllowedIds = internalHttpCommunicationAllowedIds;
    }
}
