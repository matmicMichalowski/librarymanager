package matmic.librarymanager.controllers;

import matmic.librarymanager.model.Loan;
import matmic.librarymanager.repositories.ItemRepository;
import matmic.librarymanager.repositories.LoanRepository;
import matmic.librarymanager.repositories.UserRepository;
import matmic.librarymanager.services.LoanService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    @Mock
    private Model model;

    @Mock
    private LoanService loanService;

    private IndexController controller;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private LoanRepository loanRepository;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new IndexController(loanService, userRepository, itemRepository, loanRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void getDashboardPage() throws Exception {
        Set<Loan> loans = new HashSet<>();

        loans.add(new Loan());
        loans.add(new Loan());

        when(loanService.getLoans()).thenReturn(loans);
        when(userRepository.count()).thenReturn(3L);
        when(itemRepository.count()).thenReturn(2L);
        when(loanRepository.countByBeforeDeadlineFalse()).thenReturn(3L);

        ArgumentCaptor<Set<Loan>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = controller.getDashboardPage(model);


        assertEquals("dashboard", viewName);
        verify(loanService, times(1)).getLoans();
        verify(userRepository, times(1)).count();
        verify(itemRepository, times(1)).count();
        verify(loanRepository, times(1)).countByBeforeDeadlineFalse();
        verify(model, times(1)).addAttribute(eq("loans"), argumentCaptor.capture());
        Set<Loan> controllerSet = argumentCaptor.getValue();
        assertEquals(2, controllerSet.size());

    }

    @Test
    public void updateLoanDeadlines() throws Exception {

        mockMvc.perform(get("/updateloans"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/dashboard"));

    }

}