package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.model.User;
import com.ptithcm.onlinetest.model.VerificationToken;
import com.ptithcm.onlinetest.payload.dto.UserDto;

public interface UserService {
    User registerNewUserAccount(UserDto accountDto);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

//    PasswordResetToken getPasswordResetToken(String token);

//    Optional<User> getUserByPasswordResetToken(String token);

//    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);
}
