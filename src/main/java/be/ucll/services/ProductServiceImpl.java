package be.ucll.services;

import be.ucll.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository ProductRepository;

    @Override
    public List<String> findAllProductNames() {
        return ProductRepository.findAllProductNames();
    }
}
