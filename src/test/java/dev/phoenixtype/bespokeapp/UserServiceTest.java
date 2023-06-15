package dev.phoenixtype.bespokeapp;

import dev.phoenixtype.bespokeapp.model.LoginDTO;
import dev.phoenixtype.bespokeapp.model.User;
import dev.phoenixtype.bespokeapp.model.UserDTO;
import dev.phoenixtype.bespokeapp.repository.UserRepository;
import dev.phoenixtype.bespokeapp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void testRegisterUser() {
        // Prepare test data
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("john");
        userDTO.setPassword("password");
        userDTO.setEmail("john@example.com");
        
        // Execute the method
        userService.registerUser(userDTO);
        
        // Verify that save method is called with the correct argument
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User savedUser = userCaptor.getValue();
        assertEquals("john", savedUser.getUsername());
        assertEquals("password", savedUser.getPassword());
        assertEquals("john@example.com", savedUser.getEmail());
    }
    
    @Test
    public void testLogin_WithValidCredentials() {
        // Prepare test data
        User existingUser = new User();
        existingUser.setUsername("john");
        existingUser.setPassword("password");
        
        when(userRepository.findByUsername("john")).thenReturn(existingUser);
        
        // Execute the method
        boolean loggedIn = userService.login(new LoginDTO("john", "password"));
        
        assertTrue(loggedIn);
    }
    
    @Test
    public void testLogin_WithInvalidUsername() {
        // Prepare test data
        when(userRepository.findByUsername("john")).thenReturn(null);
        
        // Execute the method
        boolean loggedIn = userService.login(new LoginDTO("john", "password"));
        
        assertFalse(loggedIn);
    }
    
    @Test
    public void testLogin_WithInvalidPassword() {
        // Prepare test data
        User existingUser = new User();
        existingUser.setUsername("john");
        existingUser.setPassword("password");
        
        when(userRepository.findByUsername("john")).thenReturn(existingUser);
        
        // Execute the method
        boolean loggedIn = userService.login(new LoginDTO("john", "wrongpassword"));
        
        assertFalse(loggedIn);
    }
}
