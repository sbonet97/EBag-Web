package es.uc3m.tiw;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Productcontroller {

	@Autowired
	Productdao productdao;
	
	// ver todos los productos
	@RequestMapping(path = "/productos", method = RequestMethod.GET)
	public ResponseEntity<?> productos(){
		ResponseEntity<?> response;
		List<Product> lista = productdao.findAll();
		if(lista == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<List<Product>>(lista, HttpStatus.OK);
		}
		return response;
	}
	
	
	// ver los productos del buscador simple
	@RequestMapping(path = "/productos/simples", method = RequestMethod.GET)
	public ResponseEntity<?> buscadorSimple(@RequestParam String search){
		ResponseEntity<?> response;
		List<Product> lista = productdao.findSimple(search);
		if(lista == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<List<Product>>(lista, HttpStatus.OK);
		}
		return response;
	}
	
	
	// ver los productos del buscador complejo
	@RequestMapping(path = "/productos/complejos", method = RequestMethod.GET)
	public ResponseEntity<?> buscadorComplejo(@RequestParam String search1, @RequestParam String search2,
			@RequestParam int search3, @RequestParam int search4){
		ResponseEntity<?> response;
		List<Product> lista = productdao.findComplex(search1, search2, search3, search4);
		if(lista == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<List<Product>>(lista, HttpStatus.OK);
		}
		return response;
	}
	
	
	// ver el producto individual
	@RequestMapping(path = "/productos/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> productoIndividual(@PathVariable("id") int id){
		ResponseEntity<?> response;
		Optional<Product> product = productdao.findById(id);
		if(product == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
		}
		return response;
	}
	
	
	// registro de producto
	@RequestMapping(path = "/productos", method = RequestMethod.POST)
	public ResponseEntity<Userebag> registroProducto(@RequestBody Product product) {
		ResponseEntity<Userebag> response;
		productdao.save(product);
		response = new ResponseEntity<>(HttpStatus.CREATED);
		return response;
	}
	
	
	// borrar producto
	@RequestMapping(path = "/productos/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> borradoProducto(@PathVariable("id") int id) {
		ResponseEntity<?> response;	
		productdao.deleteById(id);
		response = new ResponseEntity<>(HttpStatus.OK);
		return response;
	}

	
	// modificar producto
	@RequestMapping(path = "/productos", method = RequestMethod.PUT)
	public ResponseEntity<?> modificacionProducto(@RequestBody Product product){
		ResponseEntity<?> response;
		
		if(!productdao.existsById(product.getProductId())) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			productdao.save(product);
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		
		return response;
	}
	
	
	
		
	
}
