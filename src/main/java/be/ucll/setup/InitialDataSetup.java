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
            entityManager.persist(user);

            UserEntity user2 = new UserEntity();
            user2.setUsername("Cedric");
            user2.setPassword("Cedric");
            user2.setEmail("cedric7310@gmail.com");
            entityManager.persist(user2);


            //PRODUCTS
            ProductEntity product = new ProductEntity();
            product.setName("SLAB Stufful PSA 10 ");
            product.setPrice(100L);
            product.setDescription("Beautiful PSA 10 Stufful card from the MEG01 set.");
            entityManager.persist(product);

            ProductEntity product2 = new ProductEntity();
            product2.setName("SEALED Booster Box PFL 36 packs");
            product2.setPrice(260L);
            product2.setDescription("Booster box with 36 sealed PFL cards.");
            entityManager.persist(product2);

            ProductEntity product3 = new ProductEntity();
            product3.setName("ETB Scarlet & Violet – Paldea Evolved");
            product3.setPrice(55L);
            product3.setDescription("Elite Trainer Box from the Paldea Evolved set containing 9 booster packs, sleeves, and accessories.");
            entityManager.persist(product3);

            ProductEntity product4 = new ProductEntity();
            product4.setName("SLAB Charizard VMAX PSA 9 – Shiny Fates");
            product4.setPrice(180L);
            product4.setDescription("High-quality PSA 9 graded Charizard VMAX from Shiny Fates. Great collector piece.");
            entityManager.persist(product4);

            ProductEntity product5 = new ProductEntity();
            product5.setName("SEALED Pikachu V Collection Box");
            product5.setPrice(35L);
            product5.setDescription("Brand new sealed Pikachu V Collection Box including 4 booster packs and a promo.");
            entityManager.persist(product5);

            ProductEntity product6 = new ProductEntity();
            product6.setName("Pokémon 151 Binder Collection");
            product6.setPrice(45L);
            product6.setDescription("Official Pokémon 151 binder + 4 booster packs. Great for collectors.");
            entityManager.persist(product6);

            ProductEntity product7 = new ProductEntity();
            product7.setName("SLAB Eevee Promo SWSH Black Star PSA 10");
            product7.setPrice(70L);
            product7.setDescription("Gem Mint graded Eevee promo card from SWSH series. Highly collectible.");
            entityManager.persist(product7);

            ProductEntity product8 = new ProductEntity();
            product8.setName("SEALED Booster Pack Vintage XY Evolutions");
            product8.setPrice(28L);
            product8.setDescription("Authentic sealed XY Evolutions booster pack with a chance of classic reprints.");
            entityManager.persist(product8);

            ProductEntity product9 = new ProductEntity();
            product9.setName("Pokémon Scarlet & Violet Booster Bundle (6 packs)");
            product9.setPrice(28L);
            product9.setDescription("A bundle of 6 official Scarlet & Violet booster packs.");
            entityManager.persist(product9);

            ProductEntity product10 = new ProductEntity();
            product10.setName("Ultra Pro 9-Pocket Premium Binder – Eeveelutions Edition");
            product10.setPrice(25L);
            product10.setDescription("High-quality 9-pocket binder with Eeveelutions artwork. Perfect for storing cards safely.");
            entityManager.persist(product10);




            //ORDERS
            OrderEntity order = new OrderEntity();
            order.setUser(user);
            order.setTotaalBedrag(100L);
            order.setAantalProducten(1);
            order.setAfgeleverd(false);
            order.setProducts(List.of(product));
            entityManager.persist(order);


            OrderEntity order2 = new OrderEntity();
            order2.setUser(user2);
            order2.setTotaalBedrag(360L);
            order2.setAantalProducten(2);
            order2.setAfgeleverd(true);
            order2.setProducts(List.of(product2));
            entityManager.persist(order2);


            OrderEntity order3 = new OrderEntity();
            order3.setUser(user);
            order3.setTotaalBedrag(360L);
            order3.setAantalProducten(2);
            order3.setAfgeleverd(true);
            order3.setProducts(List.of(product, product2));
            entityManager.persist(order3);

            OrderEntity order4 = new OrderEntity();
            order4.setUser(user);
            order4.setTotaalBedrag(180L);
            order4.setAantalProducten(5);
            order4.setAfgeleverd(false);
            order4.setProducts(List.of(product4, product5, product6, product7, product8));
            entityManager.persist(order4);

			return null;
		});
	}
}