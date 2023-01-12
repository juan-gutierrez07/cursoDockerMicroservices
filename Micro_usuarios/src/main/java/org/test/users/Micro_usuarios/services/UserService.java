package org.test.users.Micro_usuarios.services;

import org.springframework.http.ResponseEntity;
import org.test.users.Micro_usuarios.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> userList();

    Optional<User> findById(Long id);

    User saveUser( User user);

    ResponseEntity<?>  deleteUser(Long id);

    ResponseEntity<?> editUser(User user, Long id);

    List<User> findByIds(Iterable<Long> ids);
}
