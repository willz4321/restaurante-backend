package prueba.tecnica.zoco.demo.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import prueba.tecnica.zoco.demo.models.dao.IEmpleadoRepositoryDao;
import prueba.tecnica.zoco.demo.models.dao.IMenuRepositoryDao;
import prueba.tecnica.zoco.demo.models.dao.IMesaRepositoryDao;
import prueba.tecnica.zoco.demo.models.dao.ITurnoRepositoryDao;
import prueba.tecnica.zoco.demo.models.dao.IVentaRepositoryDao;
import prueba.tecnica.zoco.demo.models.entity.Empleado;
import prueba.tecnica.zoco.demo.models.entity.Menu;
import prueba.tecnica.zoco.demo.models.entity.Mesas;
import prueba.tecnica.zoco.demo.models.entity.Turno;
import prueba.tecnica.zoco.demo.models.entity.Venta;

@Service
public class VentaService {

    private final IVentaRepositoryDao ventaRepository;
    private final ITurnoRepositoryDao turnoService;
    private final IMenuRepositoryDao menuService;
    private final IEmpleadoRepositoryDao empleadoService;
    private final IMesaRepositoryDao mesaService;
    public VentaService(IVentaRepositoryDao ventaRepository, ITurnoRepositoryDao turnoService, IMenuRepositoryDao menuService, IEmpleadoRepositoryDao empleadoService, IMesaRepositoryDao mesaService) {
        this.ventaRepository = ventaRepository;
        this.turnoService = turnoService;
        this.menuService = menuService;
        this.empleadoService = empleadoService;
        this.mesaService = mesaService;
    }

     public List<Venta> getAll() {
        return ventaRepository.findAll();
    }

    public Venta crearVenta( Venta nuevaVenta) {

     Empleado empleado = empleadoService.findById(nuevaVenta.getEmpleado().getId()).get();

     Optional<Turno> turno = turnoService.findById(nuevaVenta.getTurno().getId());

     Mesas mesa = mesaService.findById(nuevaVenta.getMesa().getId()).get();
      List<Menu> menus = new ArrayList<>();
      double totalVenta = 0.0;

      for (Menu menu : nuevaVenta.getMenusSelect()) {
        Optional<Menu> menuExistente = menuService.findById(menu.getId());
        if (!menuExistente.isPresent()) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Uno de los menús no existe");
        }
       
        menus.add(menu);
        totalVenta += menuExistente.get().getPrecio();
      }
      

       // Verifica que el empleado esté atendiendo la mesa
        if (!empleado.getMesas().contains(mesa)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado no está atendiendo la mesa");
        }

        if (!turno.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El turno no existe");
        }

        nuevaVenta.setEmpleado(empleado);
        nuevaVenta.setTurno(turno.get());
        nuevaVenta.setMenusSelect(menus);
        nuevaVenta.setTotalVenta(totalVenta);
        mesa.addTurno(turno.get());
   
      // Guarda la venta en la base de datos y devuelve la venta guardada
        return ventaRepository.save(nuevaVenta);
    }
    
}
