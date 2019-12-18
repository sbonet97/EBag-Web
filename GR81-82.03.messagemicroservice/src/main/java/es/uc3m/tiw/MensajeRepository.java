package es.uc3m.tiw;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MensajeRepository extends CrudRepository<Mensaje, String>{
	
	@Query("SELECT u FROM Mensajes u WHERE u.id=:id1 AND u.emailreceptor=mail1")
	List<Mensaje> findById(@Param("id1")String id, @Param("mail1")String emailreceptor);

	List<Mensaje> findByEmailreceptor(String emailreceptor);

}



