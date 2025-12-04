package be.ucll.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<OrderEntity> findAll() {
        return entityManager.createQuery("from OrderEntity").getResultList();
    }

    @Override
    public Collection<OrderEntity> findAllByUserId(Long userId) {
        return entityManager.createQuery("from OrderEntity where user.id = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<OrderEntity> searchOrders(Long userId, BigDecimal minAmount, BigDecimal maxAmount, Integer amountOfProducts, String productName, String email, Boolean delivered) {
        String query = "from OrderEntity o where o.user.id = :userId";

        if (minAmount != null) {
            query += " and o.totaalBedrag >= :minAmount";
        }
        if (maxAmount != null) {
            query += " and o.totaalBedrag<= :maxAmount";
        }
        if (amountOfProducts != null) {
            query += " and o.aantalProducten = :amountOfProducts";
        }
        if (productName != null && !productName.isEmpty()) {
            query += " and exists (select p from o.products p where p.name like :productName)";
        }
        if (email != null && !email.isEmpty()) {
            query += " and o.user.email like :email";
        }
        if (delivered != null) {
            query += " and o.afgeleverd = :delivered";
        }

        Query search = entityManager.createQuery(query);
        search.setParameter("userId", userId);

        if (minAmount != null) {
            search.setParameter("minAmount", minAmount);
        }
        if (maxAmount != null) {
            search.setParameter("maxAmount", maxAmount);
        }
        if (amountOfProducts != null) {
            search.setParameter("amountOfProducts", amountOfProducts);
        }
        if (productName != null && !productName.isEmpty()) {
            search.setParameter("productName", "%" + productName + "%");
        }
        if (email != null && !email.isEmpty()) {
            search.setParameter("email", email);
        }
        if (delivered != null) {
            search.setParameter("delivered", delivered);
        }

        return search.getResultList();
    }

    @Override
    public OrderEntity findById(Long orderId) {
        return entityManager.find(OrderEntity.class, orderId);
    }

}
