package prueba.tecnica.zoco.demo.models.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import prueba.tecnica.zoco.demo.models.dao.IMesaRepositoryDao;
import prueba.tecnica.zoco.demo.models.entity.Empleado;
import prueba.tecnica.zoco.demo.models.entity.Mesas;
import prueba.tecnica.zoco.demo.models.entity.Turno;

@Service
public class MesaService {

    private final IMesaRepositoryDao mesaRepository;
    private final EmpleadoService empleadoService;
     public MesaService(IMesaRepositoryDao mesaRepository, EmpleadoService empleadoService) {
        this.mesaRepository = mesaRepository;
        this.empleadoService = empleadoService;
    }

    
     public List<Mesas> getAllTotal() {
        return mesaRepository.findAll();
    }

    public List<Map<String, Object>> getAll() {
        return mesaRepository.findAll().stream()
            .map(mesa -> {
                Map<String, Object> mesaMap = new HashMap<>();
                mesaMap.put("id", mesa.getId());
                mesaMap.put("nombre", mesa.getNombre());
                List<String> nombresDeLosMozos = mesa.getMozosAll().stream()
                    .map(Empleado::getNombre) // Asegúrate de que la clase Empleado tiene un método getNombre
                    .collect(Collectors.toList());
                mesaMap.put("mozos", nombresDeLosMozos);
                List<Map<String, Object>> turnos = mesa.getTurnos().stream()
                .map(turno -> {
                    Map<String, Object> turnoMap = new HashMap<>();
                    turnoMap.put("id", turno.getId());
                    turnoMap.put("horario", turno.getHorario());
                    return turnoMap;
                })
                .collect(Collectors.toList());
            mesaMap.put("turnos", turnos);
                return mesaMap;
            })
            .collect(Collectors.toList());
    }

    public Optional<Mesas> findById(Long id) {
        return mesaRepository.findById(id);
    }
    

 public Mesas addMozo(Mesas mesa, Empleado mozo, Turno turno) {
    // Obtén las mesas del mozo
    List<Mesas> mesasMozo = mozo.getMesas();
    // Verifica si el mozo ya tiene asignada la misma mesa en el mismo turno
    for (Mesas mesaMozo : mesasMozo) {
        if (mesaMozo.getId().equals(mesa.getId())) {
            List<Turno> turnosMesa = mesaMozo.getTurnos();
            for (Turno turnoMesa : turnosMesa) {
                if (turnoMesa.getHorario().equals(turno.getHorario())) {
                    throw new RuntimeException("El mozo ya tiene asignada esta mesa en este turno.");
                }
            }
        }
    }

    // Si la mesa no ha sido asignada, añádela a las mesas del mozo
    mesa.addTurno(turno);
    //mesa.setMozos(mozo);
    mozo.getMesas().add(mesa);
    empleadoService.crearEmpleado(mozo);
    return mesa;
  }

}
