package prueba.tecnica.zoco.demo.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import prueba.tecnica.zoco.demo.models.dao.IMenuRepositoryDao;
import prueba.tecnica.zoco.demo.models.entity.Menu;
import prueba.tecnica.zoco.demo.models.entity.Mesas;

@Service
public class MenuService {
    
    private final IMenuRepositoryDao menuRepository;
    private final MesaService mesaService;
    private final EmpleadoService empleadoService;

    public MenuService(IMenuRepositoryDao menuRepository, MesaService mesaService, EmpleadoService empleadoService) {
        this.menuRepository = menuRepository;
        this.mesaService = mesaService;
        this.empleadoService = empleadoService;
    }

    public List<Menu> getMenus(){
          return menuRepository.findAll();
    }
    
   public Optional<Menu> findById(Long id){
        return menuRepository.findById(id);
    } 

    public List<Menu> seleccionarMenu(List<Long> idMenus, Long idMesa, Long idMozo) {
    // Buscar la mesa y el mozo en la base de datos
    Mesas mesa = mesaService.findById(idMesa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mesa no encontrada"));

     empleadoService.findById(idMozo).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));

   
     if (mesa.getMozos() == null || mesa.getMozosAll().isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La mesa debe tener asignado al menos un mozo");
    }
    
    List<Menu> menusSeleccionados = new ArrayList<>();
    for (Long idMenu : idMenus) {
        // Buscar el menú en la base de datos
        Menu menu = menuRepository.findById(idMenu).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menú no encontrado"));
        menusSeleccionados.add(menu);
    }

    // Guardar todos los menús seleccionados en la base de datos
    return menuRepository.saveAll(menusSeleccionados);
}

}
