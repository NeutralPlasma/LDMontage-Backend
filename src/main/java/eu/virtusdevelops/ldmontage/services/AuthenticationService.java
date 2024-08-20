package eu.virtusdevelops.ldmontage.services;


import eu.virtusdevelops.ldmontage.domain.auth.LoginData;
import eu.virtusdevelops.ldmontage.domain.token.SessionToken;
import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.repositories.SessionTokenRepository;
import eu.virtusdevelops.ldmontage.repositories.UserRepository;
import eu.virtusdevelops.ldmontage.requests.LoginRequest;
import eu.virtusdevelops.ldmontage.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final SessionTokenRepository sessionTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final StandardSessionIdGenerator sessionIdGenerator;


    /**
     * Preforms a login, throws exception if unsuccessful.
     * @param request login request
     * @return Login data (token and user)
     */
    public LoginData login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.username()).orElseThrow();
        var sessionId = sessionIdGenerator.generateSessionId();
        var token = saveUserToken(user, sessionId);
        return new LoginData(user, token);
    }

    /**
     * Tries to register new users,
     * throws errors if username already exists
     * @param request register request
     * @return the new registered user
     */
    public User register(RegisterRequest request) {
        // TODO

        var userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }


        var user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phoneNumber())
                .birthDate(request.birthDate())
                .build();
        userRepository.save(user);

        // todo: mail sending service and send email

        return user;
    }

    /**
     * Generates and saves new user session token that lasts 30 days
     * @param user for which user to create token
     * @param tokenString the token string used in token
     * @return new session token with 30 day expiration date
     */
    private SessionToken saveUserToken(User user, String tokenString) {
        var expireDate = new Date(System.currentTimeMillis() + Duration.of(30, ChronoUnit.DAYS).toMillisPart());
        var token = SessionToken.builder()
                .user(user)
                .token(tokenString)
                .expired(false)
                .revoked(false)
                .expiresOn(expireDate)
                .build();

        return sessionTokenRepository.save(token);
    }

}
