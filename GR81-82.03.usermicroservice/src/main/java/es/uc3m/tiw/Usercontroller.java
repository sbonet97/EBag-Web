package es.uc3m.tiw;

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
public class Usercontroller {

	@Autowired
	Userdao userdao;
	
	// ver usuario individual
	@RequestMapping(path = "/usuarios/{email}", method = RequestMethod.GET)
	public ResponseEntity<?> perfil(@PathVariable("email") String email){
		Userebag userebag = userdao.findByEmail(email);
		ResponseEntity<?> response;
		if (userebag == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<>(userebag, HttpStatus.OK);
		}
		return response;
	}
	
	
	// registro de usuario
	@RequestMapping(path = "/usuarios", method = RequestMethod.POST)
	public ResponseEntity<Userebag> registro(@RequestBody Userebag userebag) {
		String email = userebag.getEmail();
		ResponseEntity<Userebag> response;
		if(userdao.existsById(email)) {
			response = new ResponseEntity<>(HttpStatus.CONFLICT);
		} else {
			userdao.save(userebag);
			response = new ResponseEntity<>(HttpStatus.CREATED);
		}
		
		return response;
	}
	
	
	// inicio de sesion
	@RequestMapping(path = "/usuarios", method = RequestMethod.GET)
	public ResponseEntity<?> inicio(@RequestParam(value="email") String email, @RequestParam(value="password") String password){
		password = password.replaceAll(" ", "+");
		Userebag userebag = userdao.findByEmailAndPassword(email, password);
		ResponseEntity<?> response;
		if (userebag == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<>(userebag, HttpStatus.OK);
		}
		return response;
	}
	
	
	// borrar usuario
	@RequestMapping(path = "/usuarios/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<?> borrado(@PathVariable("email") String email) {
		ResponseEntity<?> response;	
		
		if(!userdao.existsById(email)) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			userdao.deleteById(email);
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		
		return response;
	}
	
	
	// modificar usuario
	@RequestMapping(path = "/usuarios", method = RequestMethod.PUT)
	public ResponseEntity<?> modificacion(@RequestBody Userebag userebag){
		String email = userebag.getEmail();
		ResponseEntity<?> response;
		
		if(!userdao.existsById(email)) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			userdao.save(userebag);
			response = new ResponseEntity<Userebag>(userebag, HttpStatus.OK);
		}
		
		return response;
	}
	
	
	// ver wishlist del usuario
	@RequestMapping(path = "/usuarios/{email}/wishlists", method = RequestMethod.GET)
	public ResponseEntity<?> wishlist(@PathVariable("email") String email){
		ResponseEntity<?> response;
		Userebag userebag = userdao.findByEmail(email);
		if(userebag == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Product> wishlist = userebag.getProducts2();
			response = new ResponseEntity<List<Product>>(wishlist, HttpStatus.OK);
		}
		return response;
	}
	
	
	// agregar producto a wishlist del usuario
	@RequestMapping(path = "/usuarios/{email}/wishlists", method = RequestMethod.POST)
	public ResponseEntity<?> wishlistAgregacion(@PathVariable("email") String email, @RequestBody Product product){
		ResponseEntity<?> response;
		Userebag userebag = userdao.findByEmail(email);
		if(userebag == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Product> wishlist = userebag.getProducts2();
			for(int i = 0; i<wishlist.size(); i++) {
				if(wishlist.get(i).getProductId() == product.getProductId()) {
					response = new ResponseEntity<>(HttpStatus.CONFLICT);
					return response;
				}
			}
			wishlist.add(product);
			userebag.setProducts2(wishlist);
			userebag = userdao.save(userebag);
			response = new ResponseEntity<Userebag>(userebag, HttpStatus.OK);
		}
		return response;
	}
	
	
	// eliminar producto de wishlist del usuario
	@RequestMapping(path = "/usuarios/{email}/wishlists", method = RequestMethod.DELETE)
	public ResponseEntity<?> wishlistEliminacion(@PathVariable("email") String email, @RequestParam int id){
		ResponseEntity<?> response;
		Userebag userebag = userdao.findByEmail(email);
		if(userebag == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Product> wishlist = userebag.getProducts2();
			for(int i = 0; i<wishlist.size(); i++) {
				if(wishlist.get(i).getProductId() == id) {
					wishlist.remove(i);
				}
			}
			userebag.setProducts2(wishlist);
			userebag = userdao.save(userebag);
			response = new ResponseEntity<Userebag>(userebag, HttpStatus.OK);
		}
		return response;
	}

	
	// ver myproducts del usuario vendedor
	@RequestMapping(path = "/usuarios/{email}/myproducts", method = RequestMethod.GET)
	public ResponseEntity<?> myproducts(@PathVariable("email") String email){
		ResponseEntity<?> response;
		Userebag userebag = userdao.findByEmail(email);
		if(userebag == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Product> lista = userebag.getProducts1();
			response = new ResponseEntity<List<Product>>(lista, HttpStatus.OK);
		}
		return response;
	}
	
	// lista de vendedores
	@RequestMapping(path = "/usuarios/vendedores", method = RequestMethod.GET)
	public ResponseEntity<List<Userebag>> vendedores(){
		List<Userebag> lista = userdao.findSellers();
		ResponseEntity<List<Userebag>> response;
		response = new ResponseEntity<List<Userebag>>(lista, HttpStatus.OK);
		return response;
	}
	
	// lista de compradores
	@RequestMapping(path = "/usuarios/compradores", method = RequestMethod.GET)
	public ResponseEntity<List<Userebag>> compradores(){
		List<Userebag> lista = userdao.findClients();
		ResponseEntity<List<Userebag>> response;
		response = new ResponseEntity<List<Userebag>>(lista, HttpStatus.OK);
		return response;
	}
		
	// lista de usuarios de la web
	@RequestMapping(path = "/usuarios/webusers", method = RequestMethod.GET)
	public ResponseEntity<List<Userebag>> webusers(){
		List<Userebag> lista = userdao.findWebUsers();
		ResponseEntity<List<Userebag>> response;
		response = new ResponseEntity<List<Userebag>>(lista, HttpStatus.OK);
		return response;
	}
}
