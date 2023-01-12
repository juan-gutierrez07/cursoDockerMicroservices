package org.test.users.Micro_usuarios.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.test.users.Micro_usuarios.models.User;
import org.test.users.Micro_usuarios.services.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }
    private ResponseEntity<Map<String,String>> validateResult (BindingResult result){
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errores.put(error.getField(),"el campo "+ error.getField()+" "+ error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            return validateResult(result);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> findUserid(@PathVariable Long id){
        if(userService.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value="/findAll")
    public ResponseEntity<?> finAllUser(){
        return ResponseEntity.ok().body(userService.userList());
    }

    @PutMapping(value="/edit/{id}")
    public ResponseEntity<?> editUser(@Valid @PathVariable Long id, @RequestBody User user, BindingResult result){
        System.out.println("Errores:"+ result.hasErrors());
        if(result.hasErrors()){
            return validateResult(result);
        }
        return userService.editUser(user,id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value ="/consultar-usuarios")
    public ResponseEntity<?> consultarIds (@RequestParam List<Long> ids){
        return new ResponseEntity<>(userService.findByIds(ids),HttpStatus.OK);
    }
}
