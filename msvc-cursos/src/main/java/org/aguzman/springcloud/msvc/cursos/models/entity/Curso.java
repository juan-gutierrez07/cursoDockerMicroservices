package org.aguzman.springcloud.msvc.cursos.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.aguzman.springcloud.msvc.cursos.entity.CursoUsuario;
import org.aguzman.springcloud.msvc.cursos.models.Usuario;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @NotBlank
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;

    @Transient
    private List<Usuario> usuarios;

    public Curso(){
        cursoUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void addCursoUsusario(CursoUsuario cursoUsuario){
        this.cursoUsuarios.add(cursoUsuario);
    }
    public void removeCursoUsusario(CursoUsuario cursoUsuario){
        this.cursoUsuarios.remove(cursoUsuario);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }
}
