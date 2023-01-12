package org.test.users.Micro_usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "localhost:8002/cursos")
public interface ClientCursos {

    @DeleteMapping(value = "/eliminar-usuario/{id}")
    void eliminarUsuarioPorId(@PathVariable Long id);
}
