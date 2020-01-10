package gov.fda.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("gov.fda.repository") })
public class LocalRepository implements ApplicationContextAware {
	ApplicationContext appCtx;

	@Bean(name = "dataSource")
	@Scope("singleton")
	public DataSource dataSource() throws IOException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		Properties props = getProperties();

		dataSource.setDriverClassName(props.getProperty("datasource.driver-class-name"));
		dataSource.setUrl(props.getProperty("datasource.url"));
		dataSource.setUsername(props.getProperty("datasource.username"));
		dataSource.setPassword(props.getProperty("datasource.password"));

		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws SQLException {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("gov.fda.repository");
		Properties props = null;
		try {
			props = getProperties();
			DataSource ds = dataSource();
			factory.setDataSource(dataSource());
			
		} catch (IOException e) {
			return null;
		}
		if (props.getProperty("spring.jpa.show-sql") != null) {
			vendorAdapter.setShowSql(props.getProperty("spring.jpa.show-sql").startsWith("true"));
		} else {
			vendorAdapter.setShowSql(true);
		}
		if (props.getProperty("spring.jpa.database-platform") != null) {
			vendorAdapter.setDatabasePlatform(props.getProperty("spring.jpa.database-platform"));
		} else {
			vendorAdapter.setDatabasePlatform("org.hibernate.dialect.SQLServerDialect");
		}
		factory.setJpaProperties(props);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	public Properties getProperties() throws IOException {
		Properties props = new Properties();
		String rc = String.format("file:///%s/variable.properties",
				System.getProperty("user.home").replaceAll("\\\\", "/"));
		InputStream inputStream = null;
		try {
			inputStream = new URI(rc).toURL().openStream();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (inputStream == null) {
			rc = String.format("variable.properties");
			inputStream = getClass().getClassLoader().getResourceAsStream(rc);
		}

		if (inputStream != null) {
			props.load(inputStream);
			inputStream.close();
		} else {
			throw new FileNotFoundException("program property file not found.");
		}
		return props;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		appCtx = arg0;
	}
}
