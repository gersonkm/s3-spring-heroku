package br.com.s3springheroku.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    List<User> findAll();

}
