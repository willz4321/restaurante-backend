package prueba.tecnica.zoco.demo.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import prueba.tecnica.zoco.demo.models.dao.IRolRepositoryDao;
import prueba.tecnica.zoco.demo.models.entity.Rol;
import prueba.tecnica.zoco.demo.models.entity.Roles;

@Service
public class RolService {

    private final IRolRepositoryDao rolRepository;

    public RolService(IRolRepositoryDao rolRepository) {
        this.rolRepository = rolRepository;
    }

     public List<Rol> getAll() {
        return rolRepository.findAll();
    }

    public Rol findByNombre(Roles nombre) {
    return rolRepository.findByNombre(nombre);
}
    public Optional<Rol> findById(Long id){
        return rolRepository.findById(id);
    }
}
