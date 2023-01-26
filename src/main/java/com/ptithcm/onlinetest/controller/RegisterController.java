package com.ptithcm.onlinetest.controller;

import com.ptithcm.onlinetest.model.User;
import com.ptithcm.onlinetest.payload.dto.UserDto;
import com.ptithcm.onlinetest.registration.OnRegistrationCompleteEvent;
import com.ptithcm.onlinetest.repository.UserRepository;
import com.ptithcm.onlinetest.repository.VerificationTokenRepository;
import com.ptithcm.onlinetest.security.JwtTokenProvider;
import com.ptithcm.onlinetest.service.UserService;
import com.ptithcm.onlinetest.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @PostMapping("/registration")
    public GenericResponse registerUserAccount(@Valid @RequestBody final UserDto accountDto, final HttpServletRequest request) {

        System.out.println(accountDto.toString());
        LOGGER.debug("Registering user account with information: {}", accountDto);
        final User registered = userService.registerNewUserAccount(accountDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(getAppUrl(request), request.getLocale(), registered));
        return new GenericResponse("success");
    }
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping("/registrationConfirm")
    public GenericResponse registrationConfirm(@Param("token") String token) {
        if(userService.verifyRegistration(token) == true) {
            return new GenericResponse("success");
        }
        return new GenericResponse("failure");
    }
}
