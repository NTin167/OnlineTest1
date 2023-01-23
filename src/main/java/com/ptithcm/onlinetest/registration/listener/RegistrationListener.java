package com.ptithcm.onlinetest.registration.listener;

import com.ptithcm.onlinetest.model.User;
import com.ptithcm.onlinetest.registration.OnRegistrationCompleteEvent;
import com.ptithcm.onlinetest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {

    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event,
                                                    final User user,
                                                    final String token) {

        return null;
    }
}
