package matmic.librarymaneger.converter;

import matmic.librarymaneger.command.LoanCommand;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.model.Loan;
import matmic.librarymaneger.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoanToLoanCommandTest {

    private static final Long ID = 2L;
    private static final Long ITEM_ID = 3L;
    private static final Long USER_ID = 1L;


    private LoanToLoanCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new LoanToLoanCommand();
    }

    @Test
    public void convert() throws Exception {

        Loan loan = new Loan();

        User user = new User();
        user.setId(USER_ID);

        Item item = new Item();
        item.setId(ITEM_ID);

        loan.setId(ID);
        loan.setUser(user);
        loan.setItem(item);

        LoanCommand loanCommand = converter.convert(loan);

        assertNotNull(loanCommand);
        assertEquals(ID, loanCommand.getId());
        assertEquals(USER_ID, loanCommand.getUserId());
        assertEquals(ITEM_ID, loanCommand.getItemId());
    }

}