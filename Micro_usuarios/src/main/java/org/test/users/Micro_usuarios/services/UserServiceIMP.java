package org.test.users.Micro_usuarios.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.test.users.Micro_usuarios.clients.ClientCursos;
import org.test.users.Micro_usuarios.models.User;
import org.test.users.Micro_usuarios.repositorys.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMP implements UserService {
    private UserRepository userRepository;
    private ClientCursos clientCursos;
    public UserServiceIMP(UserRepository userRepository, ClientCursos clientCursos){
        this.userRepository= userRepository;
        this.clientCursos = clientCursos;
    }
    @Override
    @Transactional
    public List<User> userList() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    @Transactional
    @Override
    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public ResponseEntity<?> deleteUser( Long id) {
        Optional<User> op = userRepository.findById(id);
        if(op.isPresent()){
            userRepository.delete(op.get());
            clientCursos.eliminarUsuarioPorId(id);
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> editUser(User user, Long id) {
        Optional<User> op = userRepository.findById(id);
        if(op.isPresent()){
            User userFind = op.get();
            if(!user.getEmail().equalsIgnoreCase(userFind.getEmail()) && userRepository.findByEmail(user.getEmail()).isPresent()) {
                return  ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","Ya existe un usuario con ese email"));
            }
            User us = op.get();
            us.setName(user.getName());
            us.setEmail(user.getEmail());
            us.setPassword(user.getPassword());
            userRepository.save(us);
            return ResponseEntity.ok().body(us);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public List<User> findByIds(Iterable<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }


}
