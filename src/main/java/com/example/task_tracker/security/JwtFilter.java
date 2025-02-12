package com.example.task_tracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    @Override
    // every time we have a request this filter will be executed
    protected void doFilterInternal( @NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain)
            throws ServletException, IOException {
                if(request.getServletPath().equals("/api/v1/auth")) {
                    filterChain.doFilter(request, response);
                    return;
                }
                final String authHeader = request.getHeader("Authorization");
                final String jwt;
                final String userEmail;
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                    return;
                }
                jwt = authHeader.substring(7);
                userEmail=jwtService.extractUsername(jwt);
                if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // means that the user is not authenticated yet
                    UserDetails userDetails=this.userDetailsService.loadUserByUsername(userEmail);
                    if(jwtService.isTokenValid(jwt, userDetails)) {
                        //validate token against my user
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);


    }
}
