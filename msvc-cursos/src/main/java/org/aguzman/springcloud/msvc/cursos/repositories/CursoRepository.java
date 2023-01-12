package org.aguzman.springcloud.msvc.cursos.repositories;


import org.aguzman.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CursoRepository extends CrudRepository<Curso, Long> {

    @Modifying
    @Query(value = "delete from CursoUsuario as cu where cu.usuario=:id")  //@Query("delete from CursoUsuario as cu where cu.usuario_id=?1") otra forma de definir parametros,
    void eliminarUsuarioCursoPorId(@Param("id")Long id);
}
