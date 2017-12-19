package matmic.librarymanager.services;

import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.converter.UserCommandToUser;
import matmic.librarymanager.converter.UserToUserCommand;
import matmic.librarymanager.model.User;
import matmic.librarymanager.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserToUserCommand userToUserCommand;

    @Mock
    private UserCommandToUser userCommandToUser;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userToUserCommand = new UserToUserCommand();
        userCommandToUser = new UserCommandToUser(userRepository);

        userService = new UserServiceImpl(userRepository, userToUserCommand, userCommandToUser);

    }

    @Test
    public void getUsers() throws Exception {
        User user = new User();
        Set<User> users = new HashSet<>();

        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        Set<User> foundUsers = userService.getUsers();

        assertEquals(1, foundUsers.size());
        verify(userRepository, times(1)).findAll();
        verify(userRepository, never()).findUserById(anyLong());
    }

    @Test
    public void findById() throws Exception {

        User user = new User();
        user.setId(5L);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        User found = userService.findById(5L);

        assertNotNull(found);
        assertEquals(Long.valueOf(5L), found.getId());
        verify(userRepository, times(1)).findById(anyLong());

    }

    @Test
    public void deleteById() throws Exception {
        Long toBeDeleted = 4L;

        userService.deleteById(toBeDeleted);

        verify(userRepository, times(1)).deleteById(anyLong());
        verify(userRepository, never()).delete(any());
    }


    @Test
    public void findUserCommandById() throws Exception {
        User user = new User();
        user.setId(1L);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findUserById(anyLong())).thenReturn(userOptional);

        UserCommand command = new UserCommand();
        command.setId(1L);



        UserCommand commandFound = userService.findUserCommandById(1L);

        assertNotNull(commandFound);
        verify(userRepository, times(1)).findUserById(anyLong());

    }

    @Test
    public void saveUser() throws Exception {
        User user = new User();
        user.setId(3L);

        when(userRepository.save(any())).thenReturn(user);

        UserCommand command = new UserCommand();
        command.setId(3L);

        UserCommand savedUSer = userService.saveUser(command);

        assertNotNull(savedUSer);
        verify(userRepository, times(1)).save(any());

    }

}