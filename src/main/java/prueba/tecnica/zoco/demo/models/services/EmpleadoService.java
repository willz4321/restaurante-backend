package prueba.tecnica.zoco.demo.models.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import prueba.tecnica.zoco.demo.models.dao.IEmpleadoRepositoryDao;
import prueba.tecnica.zoco.demo.models.entity.Empleado;
import prueba.tecnica.zoco.demo.models.entity.Rol;
import prueba.tecnica.zoco.demo.models.entity.Turno;


@Service
public class EmpleadoService {

    private final IEmpleadoRepositoryDao empleadoRepository;
    private final RolService rolService;
    
    public EmpleadoService(IEmpleadoRepositoryDao empleadoRepository, RolService rolService) {
        this.empleadoRepository = empleadoRepository;
        this.rolService = rolService;
    }

     public List<Empleado> getAll() {
        return empleadoRepository.findAll();
    }
    
    public Optional<Empleado> findById(Long id){
        return empleadoRepository.findById(id);
    }
    public Empleado findByUsername(String username) {
        return empleadoRepository.findByUsername(username);
    }

    public Empleado crearEmpleado(Empleado nuevoEmpleado) {
        return empleadoRepository.save(nuevoEmpleado);
    }
    
    public List<Empleado> findEmpleadosByMesaId(Long mesaId) {
    // Obtén todos los empleados
    List<Empleado> allEmpleados = empleadoRepository.findAll();

    // Filtra los empleados que están asignados a la mesa con el ID dado
    List<Empleado> empleadosEnMesa = allEmpleados.stream()
        .filter(empleado -> empleado.getMesas().stream()
            .anyMatch(mesa -> mesa.getId().equals(mesaId)))
        .collect(Collectors.toList());

    return empleadosEnMesa;
}

   public Empleado updateEmpleado(Long id, Empleado empleadoActualizado){
    // Buscar al empleado en la base de datos
    Optional<Empleado> optionalEmpleado = empleadoRepository.findById(id);
    
    if (optionalEmpleado.isPresent()) {
        Empleado empleadoExistente = optionalEmpleado.get();
        
        // Asegurarse de que el Rol exista en la base de datos
        Rol rol = rolService.findById(empleadoActualizado.getRol().getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
        
        // Verificar que los turnos del empleado no estén duplicados
        Set<Long> turnoIds = empleadoActualizado.getTurnos().stream()
            .map(Turno::getId)
            .collect(Collectors.toSet());
        if (turnoIds.size() < empleadoActualizado.getTurnos().size()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El empleado tiene turnos duplicados");
        }
        
        // Actualizar el rol y los turnos del empleado
        empleadoExistente.setRol(rol);
        empleadoExistente.setTurnos(empleadoActualizado.getTurnos());
        
        // Guardar el empleado actualizado en la base de datos
        return empleadoRepository.save(empleadoExistente);
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado");
    }
}

    public void deleteEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}
