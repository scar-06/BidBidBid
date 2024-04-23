package org.codesofscar.bidbidbid.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.codesofscar.bidbidbid.repository.UserRepository;
import org.codesofscar.bidbidbid.serviceImpl.UserServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtils utils;

    private UserServiceImpl userService;

    private UserRepository userRepository;


    public JwtAuthenticationFilter(JwtUtils utils, @Lazy UserServiceImpl userService) {
        this.utils = utils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String authorisationHeader = null;
        String username = null;
        UserDetails userDetail = null;

        authorisationHeader = request.getHeader("Authorization");

        if(authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
            token = authorisationHeader.substring(7);
            username = utils.extractUsername.apply(token);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            userDetail = userService.loadUserByUsername(username);

            if(userDetail != null && utils.isTokenValid.apply(token, userDetail.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}