package com.ptithcm.onlinetest.security;

public interface ISecurityUserService {
    String validatePasswordResetToken(String token);
}
