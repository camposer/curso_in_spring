package config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import service.IPersonaService;
import service.PersonaService;
import dao.IPersonaDao;
import dao.PersonaDao;

@EnableTransactionManagement // Habilita el manejo de transacciones
@Configuration // Esta clase contiene configuraciones
public class AppConfig {
	@Bean(name="entityManagerFactory") // Define un bean con id = entityManagerFactory
	@Scope("singleton")
	public EntityManagerFactory getEntityManagerFactory() { // El nombre del método es el id (si no tiene un Bean.name) 
		return Persistence.createEntityManagerFactory("PersonaJpa");
	}
	
	@Bean
	public EntityManager entityManager() {
		return getEntityManagerFactory().createEntityManager();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(getEntityManagerFactory());
		
		return transactionManager;
	}
	
	@Bean
	public IPersonaDao personaDao() {
		return new PersonaDao();
	}

	@Bean
	public IPersonaService personaService() {
		return new PersonaService();
	}

}
