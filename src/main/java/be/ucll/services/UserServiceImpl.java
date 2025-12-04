package be.ucll.services;

import java.util.Collection;

import be.ucll.repositories.UserEntity;
import be.ucll.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<UserEntity> findAll() {
        return userRepository.findAll();
    }
    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    
}