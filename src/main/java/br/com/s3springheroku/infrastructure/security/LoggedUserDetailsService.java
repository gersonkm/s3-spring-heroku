package br.com.s3springheroku.infrastructure.security;

import br.com.s3springheroku.domain.User;
import br.com.s3springheroku.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Implementação de {@code org.springframework.security.core.userdetails.UserDetailsService}
 * responsável pela busca do {@code org.springframework.security.core.userdetails.UserDetails}.
 */
@Component
public class LoggedUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoggedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =
                userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Usuário com email '%s' não foi encontrado.", email))
                );
        return new LoggedUser(user);
    }
    
}
