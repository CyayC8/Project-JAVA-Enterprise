package be.ucll.services;

import be.ucll.repositories.UserEntity;
import be.ucll.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

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