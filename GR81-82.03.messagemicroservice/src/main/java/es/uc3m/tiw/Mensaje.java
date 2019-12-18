package es.uc3m.tiw;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@Document(collection = "GR81-82-03-mensajes")
@JsonPropertyOrder({"emailreceptor", "emailemisor","mensaje"})
public class Mensaje implements Serializable{
	    
	@Id
	private String id;

	private String emailreceptor;
    
    private String emailemisor;
     
    private String mensaje;
    
	public Mensaje() {
		
	}

	@PersistenceConstructor
	public Mensaje(String emailreceptor, String emailemisor, String mensaje) {
		this.emailreceptor = emailreceptor;
		this.emailemisor = emailemisor;
		this.mensaje = mensaje;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getemailreceptor() {
		return emailreceptor;
	}

	public void setemailreceptor(String emailreceptor) {
		this.emailreceptor = emailreceptor;
	}

	public String getemailemisor() {
		return emailemisor;
	}

	public void setemailemisor(String emailemisor) {
		this.emailemisor = emailemisor;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "Mensaje [emailreceptor=" + emailreceptor + ", emailemisor=" + emailemisor + ", mensaje=" + mensaje
				+ "]";
	}
   
}
