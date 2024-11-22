package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.domain.token.SessionToken;
import eu.virtusdevelops.ldmontage.dto.UserDTO;
import eu.virtusdevelops.ldmontage.mappers.UserDTOMapper;
import eu.virtusdevelops.ldmontage.middleware.CookieAuthenticationFilter;
import eu.virtusdevelops.ldmontage.requests.LoginRequest;
import eu.virtusdevelops.ldmontage.requests.RegisterRequest;
import eu.virtusdevelops.ldmontage.services.implementations.AuthenticationServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationServiceImpl authenticationService;
    private final UserDTOMapper userDTOMapper;


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        var loginData = authenticationService.login(request);
        SetupCookie(response, loginData.sessionToken());
        return ResponseEntity.ok(userDTOMapper.apply(loginData.user()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        var user = authenticationService.register(request);
        return ResponseEntity.ok(userDTOMapper.apply(user));
    }


    private void SetupCookie(HttpServletResponse response, SessionToken token) {
        Cookie cookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, token.getToken());
        cookie.setHttpOnly(true);
        cookie.setDomain("*.ldmontage.eu");
        cookie.setAttribute("SameSite", "Lax");
        cookie.setSecure(true);
        cookie.setPath("/");
        int seconds = (int) ChronoUnit.SECONDS.between(
                token.getExpiresOn().toInstant(),
                LocalDateTime.now().toInstant(ZoneOffset.MIN)
        );
        cookie.setMaxAge(seconds);
        response.addCookie(cookie);
    }
}
