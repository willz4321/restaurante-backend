package prueba.tecnica.zoco.demo.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import prueba.tecnica.zoco.demo.models.entity.Mesas;

public interface IMesaRepositoryDao extends JpaRepository<Mesas, Long>{
    Optional<Mesas> findById(Long id);
}
