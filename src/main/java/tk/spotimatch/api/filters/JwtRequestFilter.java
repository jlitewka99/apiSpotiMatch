package tk.spotimatch.api.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tk.spotimatch.api.service.MyUserDetailService;
import tk.spotimatch.api.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }

        logUserIfNotLogged(jwt, (userDetails -> {
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            return usernamePasswordAuthenticationToken;
        }));

        chain.doFilter(request, response);
    }

    public String fetchJwt(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return "";
    }

    public boolean isUserLogged() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public void logUserIfNotLogged(
            String jwt,
            Function<UserDetails, UsernamePasswordAuthenticationToken> userDetailsConsumer) {
        if (jwt == null) {
            return;
        }
        var username = jwtUtil.extractUsername(jwt);

        if (username == null || isUserLogged()) {
            return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (jwtUtil.validateToken(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = userDetailsConsumer.apply(userDetails);
            SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    public Optional<UserDetails> getUserDetailsFromJwt(String jwt) {
        if (jwt == null) {
            return Optional.empty();
        }

        var username = jwtUtil.extractUsername(jwt);

        if (username == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(this.userDetailsService.loadUserByUsername(username));
    }

}
