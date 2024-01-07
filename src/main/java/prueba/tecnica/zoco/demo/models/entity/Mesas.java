package prueba.tecnica.zoco.demo.models.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Mesas")
public class Mesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "mesa")
    private List<Turno> turnos;
    
    @ManyToOne
    @JoinColumn(name = "mozo_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Empleado mozos;

    @ManyToMany
    private List<Empleado> mozosAll;
    // otros campos y métodos

    public List<Empleado> getMozosAll() {
        return mozosAll;
    }

    public void setMozosAll(List<Empleado> mozosAll) {
        this.mozosAll = mozosAll;
    }

    public Empleado getMozos() {
        return mozos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setMozos(Empleado mozos) {
        this.mozos = mozos;
    }
      
    public Long getId() {
        return id;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
    
     public void addTurno(Turno turno) {
        if (this.turnos == null) {
            this.turnos = new ArrayList<>();
        }
        this.turnos.add(turno);
        turno.setMesa(this);
    }
    
}
