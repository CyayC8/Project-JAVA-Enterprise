package be.ucll.setup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.IntStream;

import be.ucll.repositories.OrderEntity;
import be.ucll.repositories.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import be.ucll.repositories.TestEntity;

@Component
//hier worden records in de db gestoken
public class InitialDataSetup {

	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	public void setup() {
		TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
		transactionTemplate.execute(e -> {

			IntStream.range(0, 6).forEach(value -> {
				TestEntity testEntity = new TestEntity();
				testEntity.setValue("This is value nr " + value + " created at " + new SimpleDateFormat().format(new Date()));
                testEntity.setNummer(5);
				entityManager.persist(testEntity);
			});


            UserEntity user = new UserEntity();
            user.setUsername("test");
            user.setPassword("test");
            user.setEmail("cedric7310@gmail.com");
            entityManager.persist(user);

            OrderEntity order = new OrderEntity();
            order.setUser(user);
            order.setTotaalBedrag(1000L);
            order.setAantalProducten(1);
            entityManager.persist(order);







			/**
			 * Hier kan je meer data setup in plaatsen van het moment je datamodel klaar is
			 */

			return null;
		});
	}
}