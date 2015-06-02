package br.com.s3springheroku.infrastructure.repository;

import br.com.s3springheroku.domain.Email;
import br.com.s3springheroku.domain.User;
import br.com.s3springheroku.domain.UserRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StaticUserRepository implements UserRepository {

    private ImmutableList<User> users =
            ImmutableList.<User>builder()
                    .add(new User(new Email("gerson@sidlar.com.br"), "Gerson K. M.", BCrypt.hashpw("a", BCrypt.gensalt())))
                    .add(new User(new Email("bruno@sidlar.com.br"), "Bruno Farias", BCrypt.hashpw("a", BCrypt.gensalt())))
                    .add(new User(new Email("admilson@sidlar.com.br"), "Admilson Ernesto", BCrypt.hashpw("a", BCrypt.gensalt())))
                    .add(new User(new Email("rodrigosis@sidlar.com.br"), "Rodrigo VB", BCrypt.hashpw("a", BCrypt.gensalt())))
                    .add(new User(new Email("fernandosis@sidlar.com.br"), "Fernando Campos", BCrypt.hashpw("a", BCrypt.gensalt())))
            .build();

    @Override
    public Optional<User> findByEmail(String emailAddress) {
        return users.stream()
                .filter(u -> u.getEmail().equals(emailAddress))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
