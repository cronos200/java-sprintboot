package com.app.accommodations.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.shared.adapters.exception.ResourceNotFoundException;

import com.app.users.domain.IUserRepository;
import com.app.users.domain.IUserService;
import com.app.users.domain.User;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //buscar todos
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //buscar por id
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with ID: " + id));
    }

    //guardar
    @Override
    @Transactional
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("email registred: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    //Actualizar
    @Override
    @Transactional
    public User update(User user, Long id) {
        User existingUser = findById(id);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    //Borrar por id
    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
