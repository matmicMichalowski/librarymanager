package matmic.librarymaneger.converter;

import matmic.librarymaneger.command.ItemCommand;
import matmic.librarymaneger.command.LoanCommand;
import matmic.librarymaneger.command.UserCommand;
import matmic.librarymaneger.model.Loan;
import matmic.librarymaneger.services.ItemService;
import matmic.librarymaneger.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoanCommandToLoanTest {

    private static final Long ID = 2L;
    private static final Long ITEM_ID = 3L;
    private static final Long USER_ID = 1L;


    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;


    private LoanCommandToLoan converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new LoanCommandToLoan(itemService, userService);

    }


    @Test
    public void convert() throws Exception {
        LoanCommand loanCommand = new LoanCommand();

        UserCommand userCmd = new UserCommand();
        userCmd.setId(USER_ID);

        ItemCommand itemCmd = new ItemCommand();
        itemCmd.setId(ITEM_ID);

        loanCommand.setId(ID);
        loanCommand.setUserId(userCmd.getId());
        loanCommand.setItemId(itemCmd.getId());


        Loan loan = converter.convert(loanCommand);

        assertNotNull(loan);
        assertEquals(ID, loan.getId());
        assertEquals(USER_ID, loan.getUser().getId());
        assertEquals(ITEM_ID, loan.getItem().getId());
    }

}