package org.aguzman.springcloud.msvc.cursos.controllers;


import feign.FeignException;
import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.aguzman.springcloud.msvc.cursos.models.entity.Curso;
import org.aguzman.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/cursos")
public class CursoController {
    @Autowired
 private CursoService service;
    @PutMapping(value="/asignar-usuariocurso/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> op;
        try {
            op = service.asignarUsuario(usuario,cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje","no existe " +
                    "El usuario  o no responde el servicio"+ e.getMessage()));
        }
        if(op.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(op.get());

        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/agregar-usuariocurso/{cursoId}")
    public ResponseEntity agregarUsuarioCurso(@RequestBody Usuario user, @PathVariable Long cursoId){
        Optional<Usuario> op;
        try {
            op = service.crearUsuario(user,cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje","no pudo crear " +
                    "El usuario  o no responde el servicio"));
        }
        if(op.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(op.get());

        return ResponseEntity.notFound().build();
    }
    @DeleteMapping(value="/eliminar-usuariocurso/{cursoId}")
    public ResponseEntity eliminarUsuarioCurso(@RequestBody Usuario user, @PathVariable Long cursoId){
        Optional<Usuario> op;
        try {
            op = service.eliminarUsuario(user,cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje","no se pudo eliminar " +
                    "El usuario  o no responde el servicio"));
        }
        if(op.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(op.get());

        return ResponseEntity.notFound().build();
    }
    private ResponseEntity<Map<String,String>> validateResult (BindingResult result){
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errores.put(error.getField(),"el campo "+ error.getField()+" "+ error.getDefaultMessage());
        });
        System.out.println("Entro al validate"+ errores.size()
        );
        return ResponseEntity.badRequest().body(errores);
    }
    @GetMapping(value = "/list-cursos-users/{id}")
    public ResponseEntity<?> listarConUsuarios(@PathVariable Long id){
        return ResponseEntity.ok(service.findPorIdUsuarios(id));
    }
    @GetMapping(value = "/list")
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        System.out.println("Errores.."+result.hasErrors());
        if(result.hasErrors()){
            return validateResult(result);
        }
        Curso cursoDb = service.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()){
           return validateResult(result);
        }
        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            Curso cursoDb = o.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/eliminar-usuario/{id}")
    public ResponseEntity<?> eliminarUsuarioById(@PathVariable Long id){
        service.eliminarUsuarioCursoById(id);
        return ResponseEntity.noContent().build();
    }
}
