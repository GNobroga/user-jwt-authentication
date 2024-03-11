package com.apiauth.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.apiauth.abstraction.IUserRepository;
import com.apiauth.concrete.User;
import com.apiauth.exceptions.TokenProcessingException;
import com.apiauth.interfaces.IAuthenticationService;
import com.apiauth.security.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import static com.apiauth.configuration.SecurityConfig.PERMITTED_ROUTES;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IAuthenticationService authenticationService;

    private final IUserRepository userRepository;

    private final ModelMapper modelMapper;

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
           var isPermittedRoute = PERMITTED_ROUTES.entrySet().stream().anyMatch(entry -> {
                String route = entry.getKey().replace("/**", "");
                List<String> allowedMethods = entry.getValue();

                boolean pathsMatch = request.getServletPath().startsWith(route);

                boolean methodMatches = allowedMethods.stream().anyMatch(method -> request.getMethod().equals(method));

                return pathsMatch && methodMatches;
            }); 

        if (isPermittedRoute) {
            filterChain.doFilter(request, response);
            return;
        }
    
        var authorization = request.getHeader("Authorization");

        try {
            if (isAuthorizationHeaderValid(authorization)) {
                logger.info("The header has been validated");
                var token = extractToken(authorization);

                if (authenticationService.isRefreshToken(token) || !authenticationService.isNonExpired(token)) {
                    handleResponse(response, HttpStatus.UNAUTHORIZED, "Token invalid");
                    return;
                }

                var decodedToken = authenticationService.decodeToken(token);
                var sub = decodedToken.getClaim("sub").asString();
                var applicationUserOptional = userRepository.findByUsername(sub)
                        .map(this::mapToApplicationUser);

                if (applicationUserOptional.isEmpty()) {
                    handleResponse(response, HttpStatus.UNAUTHORIZED, "User not found");
                    return;
                }

                var applicationUser = applicationUserOptional.get();
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        applicationUser.getUsername(), applicationUser.getPassword(), applicationUser.getAuthorities()
                );
                logger.info("The token has been validated");
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (TokenProcessingException e) {
            logger.info("Crashed on invalid token");
            handleResponse(response, HttpStatus.UNAUTHORIZED, "Token invalid");
        } catch (Exception e) {
            logger.info("Crashed on internal server error");
            handleResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

   private void handleResponse(HttpServletResponse response, HttpStatus httpStatus, String message)
            throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("title", "API Guard");
        responseBody.put("message", message);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(objectMapper.writeValueAsString(responseBody));
        printWriter.flush();  
    }

    
    private boolean isAuthorizationHeaderValid(String authorization) {
        return Objects.nonNull(authorization) && authorization.startsWith("Bearer ") && authorization.split("Bearer ").length > 1;
    }

    private String extractToken(String authorization) {
        return authorization.replaceAll("Bearer ", "").trim();
    }

    private ApplicationUser mapToApplicationUser(User user) {
        return modelMapper.map(user, ApplicationUser.class);
    }
}
