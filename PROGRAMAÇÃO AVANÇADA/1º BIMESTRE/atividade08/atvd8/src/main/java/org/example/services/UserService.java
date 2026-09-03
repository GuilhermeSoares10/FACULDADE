package org.example.services;

import org.example.entities.User;
import org.example.exceptions.EntityNotFoundException;
import org.example.exceptions.ValidationException;
import org.example.repository.UserRepository;
import org.example.utils.ValidationUtils;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String name, String email, String password, String role) {
        ValidationUtils.validateNotBlank(name, "nome");
        ValidationUtils.validateEmail(email);
        ValidationUtils.validateMinLength(password, 6, "senha");
        ValidationUtils.validateNotBlank(role, "perfil");

        if (userRepository.existsByEmail(email)) {
            throw new ValidationException("Já existe um usuário cadastrado com o e-mail: " + email);
        }

        User user = new User(name.trim(), email.trim(), password.trim(), role.trim());
        userRepository.save(user);
        return user;
    }

    public User authenticate(String email, String password) {
        ValidationUtils.validateEmail(email);
        ValidationUtils.validateNotBlank(password, "senha");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o e-mail: " + email));

        if (!user.getPassword().equals(password.trim())) {
            throw new ValidationException("Senha incorreta.");
        }

        return user;
    }

    public User getUserById(UUID id) {
        ValidationUtils.validateNotNull(id, "ID do usuário");
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID id, String name, String email, String password, String role) {
        User existingUser = getUserById(id);

        if (name != null && !name.trim().isEmpty()) {
            existingUser.setName(name.trim());
        }

        if (email != null && !email.trim().isEmpty()) {
            ValidationUtils.validateEmail(email);
            if (!existingUser.getEmail().equalsIgnoreCase(email.trim()) && userRepository.existsByEmail(email)) {
                throw new ValidationException("O e-mail " + email + " já está em uso por outro usuário.");
            }
            existingUser.setEmail(email.trim());
        }

        if (password != null && !password.trim().isEmpty()) {
            ValidationUtils.validateMinLength(password, 6, "senha");
            existingUser.setPassword(password.trim());
        }

        if (role != null && !role.trim().isEmpty()) {
            existingUser.setRole(role.trim());
        }

        userRepository.update(existingUser);
        return existingUser;
    }

    public void deleteUser(UUID id) {
        getUserById(id); // Ensures user exists
        userRepository.deleteById(id);
    }
}
