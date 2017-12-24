package matmic.librarymanager.controllers;

import matmic.librarymanager.command.LoanCommand;
import matmic.librarymanager.model.User;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.LoanService;
import matmic.librarymanager.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoanControllerTest {

    @Mock
    private Model model;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @Mock
    private LoanService loanService;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        LoanController controller = new LoanController(userService, itemService, loanService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void searchForItemToBorrow() throws Exception {

        User user = new User();
        user.setId(2L);

        when(userService.findById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/user/2/loan/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/newloan"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("loan"));
    }


    @Test
    public void saveLoan() throws Exception {
        LoanCommand loanCommand = new LoanCommand();
        loanCommand.setId(2L);
        loanCommand.setUserId(3L);
        loanCommand.setItemId(2L);

        User user = new User();
        user.setId(3L);

        when(loanService.saveLoan(any())).thenReturn(loanCommand);

        mockMvc.perform(post("/saveloan")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "2")
                .param("userId", "3")
                .param("itemId", "2")).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/3/display"));
    }

    @Test
    public void deleteLoan() throws Exception {

        mockMvc.perform(get("/user/2/loan/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/2/display"));

        verify(loanService, times(1)).deleteLoanById(anyLong());
    }

}