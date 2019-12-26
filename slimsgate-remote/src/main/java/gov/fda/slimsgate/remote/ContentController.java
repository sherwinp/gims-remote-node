package gov.fda.slimsgate.remote;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class ContentController {
	@Autowired
	private ContentRepository contentRepository;
    @PersistenceContext(unitName = "default")
    private EntityManager em;
	
	@GetMapping("/Content/{id}/{limit}")
	public ResponseEntity<Object[]> findContentByIdLimitedTo(@PathVariable(value = "id") String contentId, @PathVariable(value="limit") int limit) {
		Query query = em.createQuery("SELECT cntn FROM Content cntn WHERE cntn_id=?1 ORDER BY cntn_pk DESC", Content.class);
		query.setParameter(1, contentId);
		query.setMaxResults(limit);
		List<Content> list = query.getResultList();	
		return new ResponseEntity(
					list,
	                HttpStatus.OK);
	
	}
}
