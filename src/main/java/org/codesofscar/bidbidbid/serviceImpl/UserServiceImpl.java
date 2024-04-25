package org.codesofscar.bidbidbid.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codesofscar.bidbidbid.dto.UserDto;
import org.codesofscar.bidbidbid.model.Users;
import org.codesofscar.bidbidbid.repository.UserRepository;
import org.codesofscar.bidbidbid.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

//    private final JwtUtils jwtUtils;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public Users saveUser(UserDto userDto) {
        Users user = new ObjectMapper().convertValue(userDto, Users.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setBidId(userDto.getBidId());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }
}
