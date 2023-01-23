package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.exception.UserAlreadyExistException;
import com.ptithcm.onlinetest.model.PasswordResetToken;
import com.ptithcm.onlinetest.model.Role;
import com.ptithcm.onlinetest.model.User;
import com.ptithcm.onlinetest.model.VerificationToken;
import com.ptithcm.onlinetest.payload.dto.UserDto;
import com.ptithcm.onlinetest.repository.PasswordResetTokenRepository;
import com.ptithcm.onlinetest.repository.RoleRepository;
import com.ptithcm.onlinetest.repository.UserRepository;
import com.ptithcm.onlinetest.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordResetTokenRepository resetTokenRepository;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

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
        VerificationToken token = tokenRepository.findByToken(verificationToken);
        if(token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if(verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordResetToken = resetTokenRepository.findByUser(user);

        if(resetTokenRepository != null) {
            resetTokenRepository.delete(passwordResetToken);
        }

        userRepository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        verificationToken.updateToken(UUID.randomUUID().toString());
        verificationToken = tokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        resetTokenRepository.save(passwordResetToken);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return resetTokenRepository.findByToken(token);
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(resetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public Optional<User> getUserByID(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if(verificationToken == null) {
            return TOKEN_INVALID;
        }
        final User user = verificationToken.getUser();

        final Calendar calendar = Calendar.getInstance();
        if(verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(false);
        userRepository.save(user);
        return TOKEN_VALID;
    }
}
