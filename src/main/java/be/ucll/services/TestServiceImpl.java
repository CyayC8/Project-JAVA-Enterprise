package be.ucll.services;

import be.ucll.repositories.TestEntity;
import be.ucll.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Override
    public Collection<TestEntity> findAll() {
        return testRepository.findAll();
    }
}
