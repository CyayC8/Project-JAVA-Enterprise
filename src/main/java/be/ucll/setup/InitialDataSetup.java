package be.ucll.setup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import be.ucll.repositories.OrderEntity;
import be.ucll.repositories.ProductEntity;
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

        List<ProductEntity> products = new ArrayList<>();
        List<OrderEntity> orders = new ArrayList<>();

		TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
		transactionTemplate.execute(e -> {

			IntStream.range(0, 6).forEach(value -> {
				TestEntity testEntity = new TestEntity();
				testEntity.setValue("This is value nr " + value + " created at " + new SimpleDateFormat().format(new Date()));
                testEntity.setNummer(5);
				entityManager.persist(testEntity);
			});


            //USERS
            UserEntity user = new UserEntity();
            user.setUsername("test");
            user.setPassword("test");
            user.setEmail("cedric7310@gmail.com");
            user.setOrders(orders);
            entityManager.persist(user);

            UserEntity user2 = new UserEntity();
            user2.setUsername("Cedric");
            user2.setPassword("Cedric");
            user2.setEmail("cedric7310@gmail.com");
            entityManager.persist(user2);


            //ORDERS
            OrderEntity order = new OrderEntity();
            order.setUser(user);
            order.setTotaalBedrag(100L);
            order.setAantalProducten(1);
            order.setAfgeleverd(false);
            order.setProducts(products);

            orders.add(order);

            OrderEntity order2 = new OrderEntity();
            order2.setUser(user);
            order2.setTotaalBedrag(200L);
            order2.setAantalProducten(2);
            order2.setAfgeleverd(true);
            order2.setProducts(products);

            orders.add(order2);



            //PRODUCTS
            ProductEntity product = new ProductEntity();
            product.setOrders(orders);
            product.setName("SLAB Stufful PSA 10 ");
            product.setPrice(100L);
            product.setDescription("Beautiful PSA 10 Stufful card from the MEG01 set.");

            ProductEntity product2 = new ProductEntity();
            product2.setOrders(orders);
            product2.setName("SEALED Booster Box PFL 36 packs");
            product2.setPrice(260L);
            product2.setDescription("Booster box with 36 sealed PFL cards.");


            products.add(product);
            products.add(product2);

            order.setProducts(products);

            entityManager.persist(order);
            entityManager.persist(order2);
            entityManager.persist(product);
            entityManager.persist(product2);

			return null;
		});
	}
}