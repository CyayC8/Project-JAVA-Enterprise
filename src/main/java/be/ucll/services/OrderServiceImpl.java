package be.ucll.services;

import be.ucll.repositories.OrderEntity;
import be.ucll.repositories.OrderRepository;
import com.vaadin.flow.component.treegrid.CollapseEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Service
@Transactional//all dbbewerkingen in transaction = alle bewerkingen in 1 transacite indien rollback ALLE wijziginen terugdraaien = db consistent

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Collection<OrderEntity> findAll() {
        return orderRepository.findAll();
    }

    public Collection<OrderEntity> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);

    }

    @Override
    public List<OrderEntity> searchOrders(Long userId, BigDecimal minAmount, BigDecimal maxAmount, Integer amountOfProducts, String productName, String email, Boolean delivered) {
        return orderRepository.searchOrders(userId, minAmount, maxAmount, amountOfProducts, productName, email, delivered);
    }

    @Override
    public OrderEntity findById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
