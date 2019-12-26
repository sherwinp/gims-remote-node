package gov.fda.slimsgate.remote;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.component.servlet.springboot.ServletMappingAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class,
		ServletMappingAutoConfiguration.class })
@ComponentScan(basePackages = "gov.fda.slimgate.remote, gov.fda.slimsgate")
public class Application extends SpringBootServletInitializer {
	private static Class applicationClass = Application.class;

	public static void main(String[] args) {
		gov.fda.slimsgate.remote.SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	@Bean
	ServletRegistrationBean<CamelHttpTransportServlet> servletRegistrationBean() {
		ServletRegistrationBean<CamelHttpTransportServlet> servlet;
		servlet = new ServletRegistrationBean<CamelHttpTransportServlet>(new CamelHttpTransportServlet(), "/run/*");
		servlet.setName("CamelServlet");
		return servlet;
	}
}
