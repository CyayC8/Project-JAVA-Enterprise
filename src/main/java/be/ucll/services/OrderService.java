package be.ucll.services;

import be.ucll.repositories.OrderEntity;

import java.util.Collection;

public interface OrderService {

    Collection<OrderEntity> findAll();
}
