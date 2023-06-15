package dev.phoenixtype.bespokeapp.service;

import dev.phoenixtype.bespokeapp.model.LoginDTO;
import dev.phoenixtype.bespokeapp.model.User;
import dev.phoenixtype.bespokeapp.model.UserDTO;
import dev.phoenixtype.bespokeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    
    public void registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        
        userRepository.save(user);
    }
    
    public boolean login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
//        return user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
        return user != null && Objects.equals(loginDTO.getPassword(), user.getPassword());
    }
}
