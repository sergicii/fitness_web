package com.fitness.service;

import com.fitness.dao.UserDAO;
import com.fitness.enums.UserRol;
import com.fitness.factory.DAOFactory;
import com.fitness.model.user.Client;
import com.fitness.model.user.Employee;
import com.fitness.model.user.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserService {
    private final UserDAO userDAO = DAOFactory.getDAO(UserDAO.class);

    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userDAO.findByEmail(email);

        if (userOpt.isPresent() && BCrypt.checkpw(password, userOpt.get().getPassword())) {
            return userOpt;
        }

        return Optional.empty();
    }

    public User register(String email, String password, UserRol rol) {
        User user = User.builder()
                .email(email)
                .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                .rol(rol)
                .build();
        userDAO.create(user);
        return user;
    }

    public Optional<Employee> getEmployeeFromUser(User user) {
        if (user == null || user.getId() == null) {
            return Optional.empty();
        }
        return userDAO.findByUserId(user.getId());
    }

    public Optional<Client> getClientFromUser(User user) {
        if (user == null || user.getId() == null) {
            return Optional.empty();
        }
        return userDAO.findByUserIdClient(user.getId());
    }

    public boolean changePassword(String email, String password, String newPassword) {
        Optional<User> userOptional = userDAO.findByEmail(email);

        if (userOptional.isEmpty() || BCrypt.checkpw(password, userOptional.get().getPassword())) {
            return false;
        }

        userOptional.get().setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        return userDAO.update(userOptional.get());
    }
}
