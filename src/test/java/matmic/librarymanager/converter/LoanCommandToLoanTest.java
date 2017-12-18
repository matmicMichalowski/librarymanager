package matmic.librarymanager.converter;

import matmic.librarymanager.command.ItemCommand;
import matmic.librarymanager.command.LoanCommand;
import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.model.Loan;
import matmic.librarymanager.repositories.ItemRepository;
import matmic.librarymanager.repositories.UserRepository;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.ItemServiceImpl;
import matmic.librarymanager.services.UserService;
import matmic.librarymanager.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoanCommandToLoanTest {

    private static final Long ID = 5L;
    private static final Long ITEM_ID = 4L;
    private static final Long USER_ID = 6L;


    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;


    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    private UserCommand user;

    private ItemCommand item;


    private LoanCommandToLoan converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, new UserToUserCommand(), new UserCommandToUser(userRepository));
        itemService = new ItemServiceImpl(itemRepository, new ItemCommandToItem(itemRepository), new ItemToItemCommand());
        converter = new LoanCommandToLoan(itemService, userService);

        user = new UserCommand();
        user.setId(USER_ID);
        userService.saveUser(user);

        item = new ItemCommand();
        item.setId(ITEM_ID);
        itemService.saveItem(item);

        System.out.println(userService.getUsers().size() + "  " + itemService.getItems().size());
    }


    @Test
    public void convert() throws Exception {
        LoanCommand loanCommand = new LoanCommand();


        loanCommand.setId(ID);
        loanCommand.setUserId(user.getId());
        loanCommand.setItemId(item.getId());

        Loan loan = converter.convert(loanCommand);

        assertNotNull(loan);
        assertEquals(ID, loan.getId());
        assertEquals(USER_ID, loan.getUser().getId());
        assertEquals(ITEM_ID, loan.getItem().getId());
    }

}