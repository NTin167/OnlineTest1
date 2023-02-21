package com.ptithcm.onlinetest.security;

import com.ptithcm.onlinetest.model.PasswordResetToken;
import com.ptithcm.onlinetest.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
@Service
public class UserSecurityService implements ISecurityUserService{

    @Autowired
    PasswordResetTokenRepository resetTokenRepository;

    @Override
    public String validatePasswordResetToken(String token) {
        System.out.println(token);
        PasswordResetToken resetToken = resetTokenRepository.findByToken(token);
        System.out.println("token" + resetToken);
        return isTokenFound(resetToken) ? "invalidToken"
                :isTokenExpired(resetToken) ? "expired"
                :null;
    }

    private boolean isTokenFound(PasswordResetToken resetToken) {
        return resetToken==null;
    }

    private boolean isTokenExpired(PasswordResetToken passwordResetToken) {
        Calendar calendar = Calendar.getInstance();
        return passwordResetToken.getExpiryDate().before(calendar.getTime());
    }

}
