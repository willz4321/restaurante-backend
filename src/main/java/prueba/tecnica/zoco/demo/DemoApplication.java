package prueba.tecnica.zoco.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import prueba.tecnica.zoco.demo.models.dao.IRolRepositoryDao;
import prueba.tecnica.zoco.demo.models.entity.Rol;
import prueba.tecnica.zoco.demo.models.entity.Roles;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
       }  

       @Bean
       public CommandLineRunner init(IRolRepositoryDao rolRepository) {
           return args -> {
               for (Roles role : Roles.values()) {
                   // Busca el rol en la base de datos
                   Rol existingRol = rolRepository.findByNombre(role);
       
                   // Si el rol no existe, crea uno nuevo
                   if (existingRol == null) {
                       Rol rol = new Rol();
                       rol.setNombre(role);
                       rolRepository.save(rol);
                   }
               }
           };
       }
       
	

}
