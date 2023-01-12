package org.test.users.Micro_usuarios.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.test.users.Micro_usuarios.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
     Optional<User> findByEmail(String email);
}
