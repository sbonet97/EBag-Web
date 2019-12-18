package es.uc3m.tiw;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Productdao extends CrudRepository<Product,Integer> {
	
	// busca todos los productos
	public List<Product> findAll();
	
	// busca por el buscador simple
	@Query("Select p from Product p where p.description like %:search% or p.name_product like %:search%")
	public List<Product> findSimple(String search);
	
	// busca por el buscador complejo
	@Query("Select p from Product p where p.description like %:search1% and p.name_product like %:search2% and p.price_product > :search3 and p.price_product < :search4")
	public List<Product> findComplex(String search1, String search2, int search3, int search4);
	
}
