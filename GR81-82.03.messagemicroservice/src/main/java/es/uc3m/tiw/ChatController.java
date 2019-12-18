package es.uc3m.tiw;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@EnableAutoConfiguration
public class ChatController {

	@Autowired
	MensajeRepository mensajeRepository;
	
	//Leer los mensajes que ha recibido un usario
	@RequestMapping(path = "/mensajes/{emailreceptor:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> getMensajesUsuario(@PathVariable(value = "emailreceptor", required = true) String emailreceptor) {

		try {
			List<Mensaje> entityList = mensajeRepository.findByEmailreceptor(emailreceptor);

			return new ResponseEntity<>(entityList, HttpStatus.OK);

		} catch (Exception ex) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	//Enviar un mensaje
	@RequestMapping(path = "/mensajes", method = RequestMethod.POST)
	public ResponseEntity<Void> sendMensaje(@RequestBody Mensaje mensaje) {

		try {

			mensajeRepository.save(mensaje);
			return new ResponseEntity<Void>(HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	//Leer el mensaje que ha recibido un usuario por parte de otro en concreto
	@RequestMapping(path = "/mensajes/{emailreceptor:.+}/{id:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> getConversaciones(@PathVariable(value = "id", required = true) String id, 
			@PathVariable(value = "emailreceptor", required = true) String emailreceptor) {

		try {

			List<Mensaje> entityList = mensajeRepository.findById(id, emailreceptor);

			if (entityList.isEmpty()) {
				
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
				
			} else {
	
				return new ResponseEntity<>(entityList, HttpStatus.OK);
			}

		} catch (Exception e) {

			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
	}


}
