package prueba.tecnica.zoco.demo.models.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import prueba.tecnica.zoco.demo.models.entity.Mesas;
import prueba.tecnica.zoco.demo.models.entity.Turno;

public interface ITurnoRepositoryDao extends JpaRepository<Turno, Long>{
    Optional<Turno> findById(Long id);
 
    int countByMesaAndId(Mesas mesa, Long id);
    
}
