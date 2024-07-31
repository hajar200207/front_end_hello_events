import com.hello_events.Entites.User;
import com.hello_events.Repositories.UserRepository;
import com.hello_events.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    private UserService userService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(passwordEncoder);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerUser(user);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User foundUser = userService.findByUsername("testUser");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("updatedUser");
        user.setEmail("updated@email.com");
        user.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldUser");
        existingUser.setEmail("old@email.com");
        existingUser.setPassword("oldPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("updated@email.com", updatedUser.getEmail());
        verify(passwordEncoder).encode("newPassword");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testCreateAdminUserIfNotExist() {
        when(userRepository.findByUsername("admin")).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.createAdminUserIfNotExist();

        verify(userRepository).findByUsername("admin");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAdminExists() {
        when(userRepository.existsByRole(User.Role.ADMIN)).thenReturn(true);

        boolean exists = userService.adminExists();

        assertTrue(exists);
    }

    @Test
    void testGetUserIdByEmail() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@email.com");

        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));

        Long userId = userService.getUserIdByEmail("test@email.com");

        assertEquals(1L, userId);
    }

    @Test
    void testGetUserIdByEmailNotFound() {
        when(userRepository.findByEmail("nonexistent@email.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserIdByEmail("nonexistent@email.com");
        });
    }
}