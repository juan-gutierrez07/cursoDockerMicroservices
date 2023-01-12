package org.aguzman.springcloud.msvc.cursos.clients;

import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mcsv-usuarios", url = "localhost:8080")
public interface ClientUser {
    @PostMapping("/users/save")
    Usuario save(@RequestBody Usuario user);

    @GetMapping(value = "/users/find/{id}")
    Usuario findUserid(@PathVariable Long id);

    @GetMapping(value  ="/users/consultar-usuarios")
    List<Usuario> finByIds(@RequestParam List<Long> ids);
}
