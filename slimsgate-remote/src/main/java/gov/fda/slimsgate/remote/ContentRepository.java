package gov.fda.slimsgate.remote;

import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

@org.springframework.stereotype.Repository
@EnableJpaRepositories(basePackages = "gov.fda.slimsgate.remote")
public interface ContentRepository extends JpaRepository<Content, Long> {
	
	@Query(value="SELECT cntn FROM Content cntn WHERE cntn_id=?1 ORDER BY cntn_pk DESC")
	ArrayList<Object[]> findContentByIdLimitedTo(String id, int limit);
}

@Component
@PropertySource(value = "classpath:application.properties")
class Repository {
	@Autowired
    private Environment env;

	@Bean(name = "dataSource")
	@Scope("singleton")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		return dataSource;
	}

	@Bean(name="entityManagerFactory")
	@ConditionalOnBean(name = "dataSource")
	@ConditionalOnMissingBean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("gov.fda.slimsgate.remote");
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		if(env.getProperty("spring.jpa.show-sql")!=null) {
			adapter.setShowSql(env.getProperty("spring.jpa.show-sql").startsWith("true"));
		}
		adapter.setDatabasePlatform(env.getProperty("spring.jpa.database-platform"));
		em.setJpaVendorAdapter(adapter);
		return em;
	}

	@Bean
	@ConditionalOnMissingBean(type = "JpaTransactionManager")
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
}