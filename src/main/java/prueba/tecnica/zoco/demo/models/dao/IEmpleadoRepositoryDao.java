package prueba.tecnica.zoco.demo.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import prueba.tecnica.zoco.demo.models.entity.Empleado;



public interface IEmpleadoRepositoryDao extends JpaRepository<Empleado, Long>{
    Empleado findByUsername(String username);
    Optional<Empleado> findById(Empleado empleado);
}
