package es.uc3m.tiw;

import java.util.ArrayList;
import java.util.List;
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
public class Checkoutcontroller {

	@Autowired
	Checkoutdao checkoutdao;
	
	// actualizar cantidad de un producto ya agregado en el carrito
	@RequestMapping(path = "/carts/{id}/{qua}", method = RequestMethod.PUT)
	public ResponseEntity<Void> cantidades(@RequestBody Userebag userebag, 
			@PathVariable(value="id") int id, @PathVariable(value="qua") int qua) {
		List<Purchase> lista = checkoutdao.findByUserebag(userebag);
		ResponseEntity<Void> response;
		boolean found = false;
		
		for(Purchase p:lista) {
			if(null == p.getCodePurchase() && p.getProduct().getProductId() == id) {
				qua = qua + p.getQuantity();
				//clave = p.getId();
				found = true;
				p.setQuantity(qua);
				checkoutdao.save(p);
			}
		}
		
		if(found == false) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<Void>(HttpStatus.FOUND);
		}
		
		return response;
	}
	
	
	// agregar al carrito
	@RequestMapping(path = "/carts/add", method = RequestMethod.POST)
	public ResponseEntity<Void> agregacionCarrito(@RequestBody Purchase purchase) {
		ResponseEntity<Void> response;
		checkoutdao.save(purchase);
		response = new ResponseEntity<Void>(HttpStatus.OK);
		return response;
	}
	
	
	// mostrar el carrito
	@RequestMapping(path = "/carts", method = RequestMethod.POST)
	public ResponseEntity<?> mostrarCarrito(@RequestBody Userebag userebag) {
		List<Purchase> lista = checkoutdao.findByUserebag(userebag);
		ResponseEntity<?> response;
		if(lista == null) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<List<Purchase>>(lista, HttpStatus.FOUND);
		}
		
		return response;
	}
	
	
	// borrar producto del carrito
	@RequestMapping(path = "/carts/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> borrado(@PathVariable("id") int id) {
		ResponseEntity<?> response;	
		checkoutdao.deleteById(id);
		response = new ResponseEntity<>(HttpStatus.OK);
		return response;
	}
	
	
	// recalcular todas las cantidades y precios
	@RequestMapping(path = "/carts/recalculate/{id}/{qua}", method = RequestMethod.PUT)
	public ResponseEntity<Void> recalcular(@PathVariable(value="id") int id, @PathVariable(value="qua") int qua) {
		Purchase purchase = checkoutdao.findById(id).orElse(null);
		ResponseEntity<Void> response;
		purchase.setQuantity(qua);
		checkoutdao.save(purchase);
		
		response = new ResponseEntity<Void>(HttpStatus.OK);
		
		return response;
	}
	
	
	// agregar codigo 
	@RequestMapping(path = "/carts/codes", method = RequestMethod.PUT)
	public ResponseEntity<?> codigo(@RequestParam(value="code") String code, @RequestBody Userebag userebag) {
		List<Purchase> lista = checkoutdao.findByUserebag(userebag);
		for(Purchase p:lista) {
			if(p.getCodePurchase() == null) {
				p.setCodePurchase(code);
				checkoutdao.save(p);
			}
		}
		ResponseEntity<?> response;
		response = new ResponseEntity<List<Purchase>>(lista, HttpStatus.OK);
		
		return response;
	}
	
	
	// ver my purchases
	@RequestMapping(path = "/carts/mypurchases", method = RequestMethod.PUT)
	public ResponseEntity<?> mypurchases(@RequestBody Userebag userebag) {
		List<Purchase> listaAux = checkoutdao.findByUserebag(userebag);
		List<Purchase> lista = new ArrayList<Purchase>(); 
		for(Purchase p:listaAux) {
			if(p.getCodePurchase() != null) {
				lista.add(p);
			}
		}
		ResponseEntity<?> response;
		response = new ResponseEntity<List<Purchase>>(lista, HttpStatus.OK);
		
		return response;
	}
	
	
}
