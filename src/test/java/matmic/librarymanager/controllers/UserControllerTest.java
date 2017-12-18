package matmic.librarymanager.controllers;

import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.User;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Mock
    private Model model;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    private UserController controller;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new UserController(userService, itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void showUsersPanel() throws Exception {
        Set<User> users = new HashSet<>();

        users.add(new User());
        User user = new User();
        user.setId(3L);
        users.add(user);

        when(userService.getUsers()).thenReturn(users);

        ArgumentCaptor<Set<User>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = controller.showUsersPanel(model);

        assertEquals("user/userlist", viewName);
        verify(userService, times(1)).getUsers();
        verify(model, times(1)).addAttribute(eq("users"), argumentCaptor.capture());
        Set<User> controllerSet = argumentCaptor.getValue();
        assertEquals(2, controllerSet.size());
    }

    @Test
    public void showById() throws Exception {
        User user = new User();
        user.setId(1L);

        Set<Item> items = new HashSet<>();

        items.add(new Item());
        Item item = new Item();
        item.setId(2L);
        items.add(item);

        when(userService.findById(anyLong())).thenReturn(user);


        mockMvc.perform(get("/user/1/display"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userdisplay"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("items"));

    }

    @Test
    public void newUser() throws Exception {

        mockMvc.perform(get("/user/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userform"))
                .andExpect(model().attributeExists("user"));

    }

    @Test
    public void saveOrUpdate() throws Exception {

        UserCommand userCommand = new UserCommand();
        userCommand.setId(1L);

        when(userService.saveUser(any())).thenReturn(userCommand);

        mockMvc.perform(post("/saveuser")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("firstName", "Name")
                .param("email", "mail@mail.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/1/display"));

    }

    @Test
    public void updateUser() throws Exception {

        UserCommand user = new UserCommand();
        user.setId(3L);

        when(userService.findUserCommandById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/user/3/update/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userform"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void deleteUserById() throws Exception {

        mockMvc.perform(get("/user/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        verify(userService, times(1)).deleteById(anyLong());

    }

}