package be.ucll.repositories;

import java.util.Collection;

public interface OrderRepository {

    Collection<OrderEntity> findAll();

    Collection<OrderEntity> findAllByUserId(Long userId);
}
