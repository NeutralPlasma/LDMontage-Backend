package eu.virtusdevelops.ldmontage.middleware;

import eu.virtusdevelops.ldmontage.repositories.SessionTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CookieAuthenticationFilter extends OncePerRequestFilter {
    public static final String COOKIE_NAME = "auth_sid";

    private final SessionTokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional<Cookie> authCookie = Stream.of(Optional.ofNullable(request.getCookies())
                        .orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                .findFirst();

        authCookie.ifPresent(cookie -> {
            var token = tokenRepository.findByToken(cookie.getValue());
            if (token.isPresent()) {
                var tokenObject = token.get();
                if (tokenObject.isExpired() || tokenObject.isRevoked())
                    return; // expired or revoked

                var user = tokenObject.getUser();

                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        });

        filterChain.doFilter(request, response);
    }
}
