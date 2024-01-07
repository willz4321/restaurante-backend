package prueba.tecnica.zoco.demo.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import prueba.tecnica.zoco.demo.models.dao.ITurnoRepositoryDao;
import prueba.tecnica.zoco.demo.models.entity.Empleado;
import prueba.tecnica.zoco.demo.models.entity.Turno;

@Service
public class TurnoService {
   
    private final ITurnoRepositoryDao turnoRepository;

    private final EmpleadoService empleadoService;

    public TurnoService(ITurnoRepositoryDao turnoRepository, EmpleadoService empleadoService) {
        this.turnoRepository = turnoRepository;
        this.empleadoService = empleadoService;
    }
   
     public Optional<Turno> findById(Long id) {
        return turnoRepository.findById(id);
    }
    public List<Turno> getAll(){
        return turnoRepository.findAll();
    }
    public Turno turnoSave(Turno turno, Empleado mozo){
        // Verificar que el mozo no tenga más de 2 turnos
        if (mozo.getTurnos().size() < 2) {
            mozo.getTurnos().add(turno);
            empleadoService.crearEmpleado(mozo); // Actualizar el empleado con el nuevo turno
            return turno;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El mozo solo puede tener un máximo de 2 turnos");
        }
    }
        

}
