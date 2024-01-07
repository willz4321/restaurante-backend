package prueba.tecnica.zoco.demo.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import prueba.tecnica.zoco.demo.models.entity.Venta;

public interface IVentaRepositoryDao extends JpaRepository<Venta, Long> {
}
