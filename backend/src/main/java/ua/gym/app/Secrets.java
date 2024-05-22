package ua.gym.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:secrets.properties")
public class Secrets {


    @Value("${security.authentication.basic.password}")
    private String basicAuthPassword;

    @Value("${security.authentication.form.password}")
    private String formLoginPassword;

    public String getBasicAuthPassword() {
        return basicAuthPassword;
    }

    public void setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
    }

    public String getFormLoginPassword() {
        return formLoginPassword;
    }

    public void setFormLoginPassword(String formLoginPassword) {
        this.formLoginPassword = formLoginPassword;
    }
}
