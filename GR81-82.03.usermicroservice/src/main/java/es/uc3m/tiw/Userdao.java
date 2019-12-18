package es.uc3m.tiw;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Userdao extends CrudRepository<Userebag,String> {
	
	// busca a todos los usuarios
	public List<Userebag> findAll();
	
	// busca por email
	public Userebag findByEmail(String email);
	
	// busca por email y contrasenya
	public Userebag findByEmailAndPassword(String email, String password); 

	// busca todos los usuarios de tipo cliente
	@Query("Select u from Userebag u where u.role = 1")
	public List<Userebag> findClients(); 
	
	// busca todos los usuarios de tipo vendedor
	@Query("Select u from Userebag u where u.role = 2")
	public List<Userebag> findSellers(); 
	
	// busca todos los usuarios que no sean de tipo administrador
	@Query("Select u from Userebag u where u.role = 1 OR u.role = 2")
	public List<Userebag> findWebUsers();
}
