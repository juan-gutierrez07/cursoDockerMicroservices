package org.aguzman.springcloud.msvc.cursos.entity;


import javax.persistence.*;

@Entity
@Table(name = "cursos_usuarios")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="usuario_id", unique = true)
    private Long usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof  CursoUsuario))
            return false;
        CursoUsuario cur = (CursoUsuario) obj;

        return this.usuario != null && this.usuario.equals(cur.usuario);
    }
}
