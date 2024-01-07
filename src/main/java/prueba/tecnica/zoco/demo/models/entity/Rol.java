package prueba.tecnica.zoco.demo.models.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles nombre;

    @OneToMany(mappedBy = "rol")
    private List<Empleado> empleados;
    
    public Roles getNombre() {
        return nombre;
    }

    public void setNombre(Roles nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

   
}