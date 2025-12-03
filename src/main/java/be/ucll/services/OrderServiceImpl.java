package be.ucll.services;

import be.ucll.repositories.OrderEntity;
import be.ucll.repositories.OrderRepository;
import com.vaadin.flow.component.treegrid.CollapseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
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
}
