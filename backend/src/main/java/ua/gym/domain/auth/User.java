package ua.gym.domain.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import ua.gym.persistense.Identifiable;
import ua.gym.utils.Assertions;


@Entity
@Table(name = "user")
public class User extends Identifiable {

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private AuthenticationType authenticationType;

    public User(String username, String password, AuthenticationType authenticationType) {
        Assertions.assertPresent(username, password, authenticationType);
        this.username = username;
        this.password = password;
        this.authenticationType = authenticationType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // getters and setters
}