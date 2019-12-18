package es.uc3m.tiw;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface Checkoutdao extends CrudRepository<Purchase,Integer> {
	
	// busca a todos los usuarios
	public Iterable<Purchase> findAll();
	
	// busca por el usuario
	public List<Purchase> findByUserebag(Userebag userebag);
	
}
