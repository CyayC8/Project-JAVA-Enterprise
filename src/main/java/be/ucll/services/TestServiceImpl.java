package be.ucll.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.ucll.repositories.TestEntity;
import be.ucll.repositories.TestRepository;

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
