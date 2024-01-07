package prueba.tecnica.zoco.demo.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import prueba.tecnica.zoco.demo.models.entity.Rol;
import prueba.tecnica.zoco.demo.models.entity.Roles;



public interface IRolRepositoryDao extends JpaRepository<Rol, Long>{
     Rol findByNombre(Roles nombre);
}
