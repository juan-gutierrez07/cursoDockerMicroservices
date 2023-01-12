package org.aguzman.springcloud.msvc.cursos.services;

import org.aguzman.springcloud.msvc.cursos.clients.ClientUser;
import org.aguzman.springcloud.msvc.cursos.entity.CursoUsuario;
import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.aguzman.springcloud.msvc.cursos.models.entity.Curso;
import org.aguzman.springcloud.msvc.cursos.repositories.CursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService{

    private CursoRepository repository;
    private ClientUser clientUser;

    private Logger LOGGER = LoggerFactory.getLogger(CursoServiceImpl.class);
    public CursoServiceImpl (CursoRepository repository, ClientUser clientUser){
        this.repository = repository;
        this.clientUser = clientUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findPorIdUsuarios(Long id) {
        Optional<Curso> op = repository.findById(id);
        if(op.isPresent()){
            if(!op.get().getCursoUsuarios().isEmpty()){
                List<Long> ids = op.get().getCursoUsuarios().stream().map(CursoUsuario::getUsuario).collect(Collectors.toList());
                List<Usuario> users = clientUser.finByIds(ids);
                op.get().setUsuarios(users);
                return op;
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarUsuarioCursoById(Long id) {
            repository.eliminarUsuarioCursoPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        if(repository.findById(cursoId).isPresent()){
            System.out.println("Esta el id...");
            Curso curso = repository.findById(cursoId).get();
            System.out.println(usuario.toString());
            Usuario usermc = clientUser.findUserid(usuario.getId());
            LOGGER.info("Consultando Microservico user-> \t"+ usermc.toString());
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuario(usermc.getId());
            curso.addCursoUsusario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usermc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        if(repository.findById(cursoId).isPresent()){
            Curso curso = repository.findById(cursoId).get();
            Usuario usermc = clientUser.save(usuario);
            LOGGER.info("Guardando Microservico user-> \t"+ usermc.toString());
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuario(usermc.getId());
            curso.addCursoUsusario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usermc);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        if(repository.findById(cursoId).isPresent()){
            Curso curso = repository.findById(cursoId).get();
            Usuario usermc = clientUser.findUserid(usuario.getId());
            LOGGER.info("Consultando Microservico user-> \t"+ usermc.toString());
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuario(usermc.getId());
            curso.removeCursoUsusario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usermc);
        }
        return Optional.empty();
    }
}
