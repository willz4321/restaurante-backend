package prueba.tecnica.zoco.demo.controllers;

import java.util.List;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import prueba.tecnica.zoco.demo.models.entity.Empleado;
import prueba.tecnica.zoco.demo.models.entity.Menu;
import prueba.tecnica.zoco.demo.models.entity.Mesas;
import prueba.tecnica.zoco.demo.models.entity.Rol;
import prueba.tecnica.zoco.demo.models.entity.Roles;
import prueba.tecnica.zoco.demo.models.entity.Turno;
import prueba.tecnica.zoco.demo.models.entity.Venta;
import prueba.tecnica.zoco.demo.models.services.EmpleadoService;
import prueba.tecnica.zoco.demo.models.services.MenuService;
import prueba.tecnica.zoco.demo.models.services.MesaService;
import prueba.tecnica.zoco.demo.models.services.RolService;
import prueba.tecnica.zoco.demo.models.services.TurnoService;
import prueba.tecnica.zoco.demo.models.services.VentaService;


@CrossOrigin(origins = {"https://restaurantsz.netlify.app" })
@RestController
@RequestMapping("/api/restaurante")
public class ApiController {
    private final RolService rolService;
    private final EmpleadoService empleadoService;
    private final VentaService ventaService;
    private final MesaService mesaService;
    private final TurnoService turnoService;
    private final MenuService menuService;
    
    public ApiController(RolService rolService, EmpleadoService empleadoService, VentaService ventaService, MesaService mesaService, TurnoService turnoService, MenuService menuService) {
        this.rolService = rolService;
        this.empleadoService = empleadoService;
        this.ventaService = ventaService;
        this.mesaService = mesaService;
        this.turnoService = turnoService;
        this.menuService = menuService;
    }
    
    

        @PostMapping("/mesas/{idMesa}/mozos/{idMozo}/{idTurno}")
            public Mesas asignarMozo(@PathVariable Long idMesa, @PathVariable Long idMozo, @PathVariable Long idTurno) {
            Turno turno = turnoService.findById(idTurno).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turno no encontrada"));
            Mesas mesa = mesaService.findById(idMesa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mesa no encontrada"));
            Empleado mozo = empleadoService.findById(idMozo).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));

            if (!mozo.getTurnos().contains(turno)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado no tiene asignado este turno");
            }

              return mesaService.addMozo(mesa, mozo, turno);
            }

            @PostMapping("/mesas/{idMesa}/mozos/{idMozo}/menus")
                public List<Menu> seleccionarMenus(@PathVariable Long idMesa, @PathVariable Long idMozo, @RequestBody List<Long> idMenus) {
                    return menuService.seleccionarMenu(idMenus, idMesa, idMozo);
                }


        @PostMapping("/turnos/{idTurno}/mozos/{idMozo}")
            public Turno asignarTurno(@PathVariable Long idTurno, @PathVariable Long idMozo) {
            Turno turno = turnoService.findById(idTurno).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turno no encontrada"));
            Empleado mozo = empleadoService.findById(idMozo).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
            return turnoService.turnoSave(turno, mozo);
            }

    // endpoints CRUD para roles
        @GetMapping("/roles")
          public List<Rol> getAllRoles() {
            return rolService.getAll();
        }

        @PostMapping("/usuarios")
        public Empleado crearUsuario(@RequestBody Empleado nuevoUsuario) {
            // Obtiene el rol del nuevo usuario
            Roles  rolNombre = nuevoUsuario.getRol().getNombre();
        
               // Busca el rol en la base de datos
                Rol rol = rolService.findByNombre(rolNombre);

                // Verifica que el rol exista
                if (rol == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol no existe");
                }

                 // Asigna el rol al nuevo usuario
                nuevoUsuario.setRol(rol);

                // Crea el nuevo usuario
                return empleadoService.crearEmpleado(nuevoUsuario);

           }

           @PutMapping("/empleados/{id}")
            public Empleado updateEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
                return empleadoService.updateEmpleado(id, empleadoActualizado);
            }
        
    // endpoints CRUD para empleados
    @GetMapping("/empleados")
    public List<Empleado> getAllEmpleados() {
        return empleadoService.getAll();
    }
    @GetMapping("/empleados/mesa/{idMesa}")
    public List<Empleado> getEmpleadosByIdMesa(@PathVariable Long idMesa) {
        return empleadoService.findEmpleadosByMesaId(idMesa);
    }    
    @GetMapping("/empleado/{id}")
    public Empleado getEmpleado(@PathVariable Long id){
        return empleadoService.findById(id).get();
    }
    @GetMapping("/mesasTotal")
    public List<Mesas> getAllMesasTotal() {
        return mesaService.getAllTotal();
    }
    
    @GetMapping("/mesas")  
    public List<Map<String, Object>> getAllMesas() {
        return mesaService.getAll();
    }

    @GetMapping("/turnos")
    public List<Turno> geTurnos(){
        return turnoService.getAll();
    }

    @GetMapping("/menus")
    public List<Menu> getMenus(){
        return menuService.getMenus();
    }

    @GetMapping("/menus/{id}")
    public Menu getMenuById(@PathVariable Long id) {
        return menuService.findById(id).get();
    }
    

    @PostMapping("/empleados/{idEmpleado}")
    public Empleado crearEmpleado(@RequestParam Long idEmpleado, @RequestBody Empleado nuevoEmpleado) {
        Empleado empleadoActual = empleadoService.findById(idEmpleado).get();
        Roles rolActual = empleadoActual.getRol().getNombre();

        if (rolActual == Roles.CEO && (nuevoEmpleado.getRol().getNombre() == Roles.GERENTE || nuevoEmpleado.getRol().getNombre() == Roles.MOZO)) {
            return empleadoService.crearEmpleado(nuevoEmpleado);
        } else if (rolActual == Roles.GERENTE && nuevoEmpleado.getRol().getNombre() == Roles.MOZO) {
            return empleadoService.crearEmpleado(nuevoEmpleado);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para realizar esta acción");
        }
    }

     @DeleteMapping("/empleados/{idEmpleado}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long idEmpleado) {
        empleadoService.deleteEmpleado(idEmpleado);
        return ResponseEntity.noContent().build();
    }

    // endpoints CRUD para ventas
    @GetMapping("/ventas")
    public List<Venta> getAllVentas() {
        return ventaService.getAll();
    }

    @PostMapping("/ventas")
        public Venta crearVenta(@RequestBody Venta nuevaVenta) {

        Roles empleadoRol = empleadoService.findById(nuevaVenta.getEmpleado().getId()).get().getRol().getNombre();
        if (empleadoRol == Roles.MOZO || empleadoRol == Roles.GERENTE) {
            return ventaService.crearVenta(nuevaVenta);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para realizar esta acción");
        }
    }

}
