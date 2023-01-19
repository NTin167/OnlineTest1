package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.exception.UserAlreadyExistException;
import com.ptithcm.onlinetest.model.Role;
import com.ptithcm.onlinetest.model.User;
import com.ptithcm.onlinetest.model.VerificationToken;
import com.ptithcm.onlinetest.payload.dto.UserDto;
import com.ptithcm.onlinetest.repository.RoleRepository;
import com.ptithcm.onlinetest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

public class IUserService implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public User registerNewUserAccount(UserDto accountDto) {
        if(userRepository.existsByEmail(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRoles((Set<Role>) roleRepository.findByName("ROLE_USER"));
        return userRepository.save(user);
    }

    @Override
    public User getUser(String verificationToken) {
        return null;
    }

    @Override
    public void saveRegisteredUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {

    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return null;
    }

    @Override
    public VerificationToken generateNewVerificationToken(String token) {
        return null;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {

    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public void changeUserPassword(User user, String password) {

    }

    @Override
    public boolean checkIfValidOldPassword(User user, String password) {
        return false;
    }

    @Override
    public String validateVerificationToken(String token) {
        return null;
    }
}
