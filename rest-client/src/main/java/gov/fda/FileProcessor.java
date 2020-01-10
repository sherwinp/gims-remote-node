package gov.fda;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Processor;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.Registry;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import gov.fda.repository.Content;
import gov.fda.repository.ContentRepository;
import gov.fda.repository.LocalRepository;

@Component
public class FileProcessor  {
	LocalRepository repository = null;
	public FileProcessor() throws Exception {
	}
	
	@Handler
	public void process(Exchange exchange, Registry registry) throws Exception {
		repository = registry.findByType(LocalRepository.class).stream().findFirst().get();
		// File Body is JSON		
		JsonParser parser = new JsonFactory().createParser(exchange.getIn().getBody(String.class));
	
		EntityManager em = repository.entityManagerFactory().createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			new Content().store(em, parser);
			txn.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			if (txn.isActive())
				txn.rollback();
		}
	}
}
