package matmic.librarymanager.services;

import matmic.librarymanager.command.LoanCommand;
import matmic.librarymanager.converter.LoanCommandToLoan;
import matmic.librarymanager.converter.LoanToLoanCommand;
import matmic.librarymanager.model.Employee;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.Loan;
import matmic.librarymanager.model.User;
import matmic.librarymanager.model.enums.Availability;
import matmic.librarymanager.repositories.EmployeeRepository;
import matmic.librarymanager.repositories.ItemRepository;
import matmic.librarymanager.repositories.LoanRepository;
import matmic.librarymanager.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoanServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private LoanService loanService;

    private LoanCommandToLoan loanCommandToLoan;
    private LoanToLoanCommand loanToLoanCommand;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Authentication auth = new UsernamePasswordAuthenticationToken("employee@mail.com", null);
        SecurityContextHolder.getContext().setAuthentication(auth);

       loanCommandToLoan = new LoanCommandToLoan(itemService, userService);
       loanToLoanCommand = new LoanToLoanCommand();

        loanService = new LoanServiceImpl(userRepository, itemRepository, employeeRepository,
                loanRepository, loanCommandToLoan, loanToLoanCommand);
    }

    @Test
    public void saveLoan() throws Exception {

        User user = new User();
        user.setId(2L);
        Optional<User> userOptional = Optional.of(user);

        Item item = new Item();
        item.setId(3L);
        Optional<Item> itemOptional = Optional.of(item);

        LoanCommand command = new LoanCommand();
        command.setId(4L);
        command.setItemId(3L);
        command.setUserId(2L);

        Employee employee = new Employee();
        employee.setEmail("employee@mail.com");
        employee.setId(1L);

        Loan loan = new Loan();
        loan.setId(4L);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        when(itemRepository.findById(anyLong())).thenReturn(itemOptional);
        when(employeeRepository.findByEmail(anyString())).thenReturn(employee);
        when(loanRepository.save(any())).thenReturn(loan);

        LoanCommand commandToSave = loanService.saveLoan(command);

        assertNotNull(commandToSave);
        assertEquals(Long.valueOf(2L), commandToSave.getUserId());
        assertEquals(Long.valueOf(3L), commandToSave.getItemId());
        verify(userRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(1)).findByEmail(anyString());
        verify(loanRepository, times(1)).save(any());

    }

    @Test
    public void getLoans() throws Exception {
        Loan loan = new Loan();
        Set<Loan> loans = new HashSet<>();

        loans.add(loan);

        when(loanRepository.findAll()).thenReturn(loans);

        Set<Loan> foundLoans = loanService.getLoans();

        assertEquals(1, foundLoans.size());
        verify(loanRepository, times(1)).findAll();
        verify(loanRepository, never()).findLoanById(anyLong());
    }

    @Test
    public void updateLoansDeadline() throws Exception {
        LocalDate dateToCheck = LocalDate.now().minusMonths(1);
        Loan loan = new Loan();
        loan.setLoanDeadline(dateToCheck);

        Loan loan2 = new Loan();

        Set<Loan> loans = new HashSet<>();
        loans.add(loan);
        loans.add(new Loan());

        when(loanService.getLoans()).thenReturn(loans);

        loanService.updateLoansDeadline();

        assertEquals(false, loan.isBeforeDeadline());
        assertEquals(true, loan2.isBeforeDeadline());

    }

    @Test
    public void deleteLoanById() throws Exception {
        Long delete = 3L;

        Item item = new Item();
        item.setId(4L);

        Employee employee = new Employee();

        User user = new User();

        Loan loan = new Loan();
        loan.setId(delete);
        loan.setItem(item);
        loan.setUser(user);
        loan.setEmployee(employee);
        Optional<Loan> optional = Optional.of(loan);


        when(loanRepository.findById(anyLong())).thenReturn(optional);

        loanService.deleteLoanById(delete);

        assertEquals(Availability.AVAILABLE, item.getIsAvailable());
        assertEquals(0, user.getLoanLine().size());
        assertEquals(0, employee.getLoansByEmployee().size());
        verify(loanRepository, times(1)).findById(anyLong());
        verify(loanRepository, times(1)).delete(any());
        verify(loanRepository, never()).deleteAll();
    }
}