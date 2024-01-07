package prueba.tecnica.zoco.demo.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Turnos")
public class Turno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String horario;
 
    @ManyToOne
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Mesas mesa;
    
    @ManyToOne
    @JoinColumn(name = "mozo_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Empleado mozo;
    
    public Long getId() {
        return id;
    }
    public Mesas getMesa() {
        return mesa;
    }
    public void setMesa(Mesas mesa) {
        this.mesa = mesa;
    }
       
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
}
