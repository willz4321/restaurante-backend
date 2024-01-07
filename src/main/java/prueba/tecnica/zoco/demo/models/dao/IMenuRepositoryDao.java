package prueba.tecnica.zoco.demo.models.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import prueba.tecnica.zoco.demo.models.entity.Menu;
import java.util.Optional;


public interface IMenuRepositoryDao extends JpaRepository<Menu, Long> {
    Optional<Menu> findById(Long id);
}
