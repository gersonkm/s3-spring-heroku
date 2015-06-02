package br.com.s3springheroku.infrastructure.security;

import br.com.s3springheroku.domain.Email;
import br.com.s3springheroku.domain.User;

import java.util.Collections;

/**
 * Wrapper de {@code br.com.sidlar.siditil.domain.usuario.Usuario} utilizado pelo Spring Security
 * para representar {@code org.springframework.security.core.userdetails.UserDetails}.
 */
public class LoggedUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public LoggedUser(User user) {
        super(user.getEmail(), user.getPassword(), Collections.emptyList());
        this.user = user;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getEncodedEmail() {
        return user.getEncodedEmail();
    }

    public String getName() {
        return user.getName();
    }

    public User getUser() {
        return user;
    }
}
