package be.ucll.repositories;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface OrderRepository {

    Collection<OrderEntity> findAll();

    Collection<OrderEntity> findAllByUserId(Long userId);

    List<OrderEntity> searchOrders(Long userId, BigDecimal minAmount, BigDecimal maxAmount, Integer amountOfProducts, String productName, String email, Boolean delivered);

    OrderEntity findById(Long orderId);

}
