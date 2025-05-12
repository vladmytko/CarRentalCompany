package com.vladyslav.CarRentalCompany.security;

import com.vladyslav.CarRentalCompany.service.CustomUserDetailService;
import com.vladyslav.CarRentalCompany.utils.JWTUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private CustomUserDetailService customUserDetailService;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//        final String jwtToken;
//        final String userEmail;
//
//        if(authHeader == null || authHeader.isBlank()) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        jwtToken = authHeader.substring(7);
//        userEmail = jwtUtils.extractUsername(jwtToken);
//
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = customUserDetailService.loadUserByUsername(userEmail);
//            if(jwtUtils.isValidToken(jwtToken, userDetails)) {
//                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, jwtToken, userDetails.getAuthorities());
//                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                securityContext.setAuthentication(token);
//                SecurityContextHolder.setContext(securityContext);
//            }
//        }
//        filterChain.doFilter(request,response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip JWT validation for public endpoints
        // These endpoints are allowed without authentication, so we bypass the filter
        if(path.startsWith("/auth") || path.startsWith("/cars/public")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from the Authorization header (if present)
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Remove "Bearer " prefix
            try {
                //Extract username from JWT token
                username = jwtUtils.extractUsername(jwt);
            } catch (ExpiredJwtException e) {
                // Token is invalid - also respond with 403
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Invalid JWT token");
                return;
            }
        }

        // if username was extracted and user is not yer authenticated in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Load user details from database
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

            // Validate the token again using the user details (e.g., check signature, expiry)
            if(jwtUtils.isValidToken(jwt, userDetails)) {
                // Create authentication token and set it in the SecurityContext
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the filter chain (pass to next filter/controller)
        filterChain.doFilter(request, response);

    }
}
