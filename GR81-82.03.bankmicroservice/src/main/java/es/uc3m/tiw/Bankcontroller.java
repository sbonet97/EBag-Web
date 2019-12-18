package es.uc3m.tiw;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Bankcontroller {
	
	@RequestMapping(path = "/transacciones", method = RequestMethod.POST)
	public ResponseEntity<?> creacionTransaccion(@RequestBody Transaccion transaccion){
		
		String fecha = transaccion.getFechaTarjeta();
		String[] prueba = fecha.split("-");
		fecha = prueba[1] + prueba[0];
		
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMM"); 
		String nowStr = now.format(formatter);
		
		/* comprueba que:
		*  numero de tarjeta consta de 16 digitos
		*  numero de tarjeta es divisible entre 3
		*  cvv consta de 3 digitos
		*  fecha es posterior a la actual
		*/
		if ((int)(Math.log10(transaccion.getNumeroTarjeta()) + 1) != 16 
				|| transaccion.getNumeroTarjeta() % 3 != 0 
				|| (int)(Math.log10(transaccion.getCvv()) + 1) != 3
				|| nowStr.compareTo(fecha) > 0 ) {
			return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
		}
		
		// generacion del codigo
		String code = "ES-" + String.valueOf(System.currentTimeMillis()) + "R";
		CodigoTransaccion result = new CodigoTransaccion();
		result.setCodigoTransaccion(code);
		
		// todo es correcto y envia el codigo
		return new ResponseEntity<CodigoTransaccion>(result, HttpStatus.OK);
	}
	
}
