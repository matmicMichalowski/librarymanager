package matmic.librarymanager.bootstrap;

import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.Loan;
import matmic.librarymanager.model.User;
import matmic.librarymanager.model.enums.DistributionType;
import matmic.librarymanager.model.enums.ItemType;
import matmic.librarymanager.model.rolemodel.Role;
import matmic.librarymanager.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Component
public class LibraryManagerBootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final LoanRepository loanRepository;




    public LibraryManagerBootstrap(UserRepository userRepository, ItemRepository itemRepository, RoleRepository roleRepository, EmployeeRepository employeeRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.loanRepository = loanRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        itemRepository.saveAll(getBooks());
//        userRepository.saveAll(getUsers());
    }

    private List<Item> getBooks(){
        List<Item> books = new ArrayList<>();

        Item novelBook = new Item();
        novelBook.setDistributionType(DistributionType.PAPER);
        novelBook.setItemType(ItemType.BOOK);
        novelBook.setAuthor("Jhon Novy");
        novelBook.setGenre("Horror");
        novelBook.setInternationalSegregationNumber("7898-7678");
        novelBook.setPublisher("PwN");
        novelBook.setYear(1999);
        novelBook.setTitle("In the wild");
        novelBook.setReleaseNumber("3");
        books.add(novelBook);

        Item book1 = new Item();
        book1.setDistributionType(DistributionType.PAPER);
        book1.setItemType(ItemType.BOOK);
        book1.setAuthor("Thomas Doe");
        book1.setGenre("Drama");
        book1.setInternationalSegregationNumber("ISBN 978-0-596-52068-7");
        book1.setPublisher("NWP");
        book1.setYear(1909);
        book1.setTitle("No Man's Land");
        book1.setReleaseNumber("1");
        books.add(book1);

        Item book2 = new Item();
        book2.setDistributionType(DistributionType.PAPER);
        book2.setItemType(ItemType.BOOK);
        book2.setAuthor("Britany Beloved");
        book2.setGenre("Sci-Fi");
        book2.setInternationalSegregationNumber("978-2-1234-5680-3");
        book2.setPublisher("No-name");
        book2.setYear(2000);
        book2.setTitle("Milenial Sky");
        book2.setReleaseNumber("1");
        books.add(book2);

        return books;
    }

    private List<User> getUsers(){

        List<User> users = new ArrayList<>();


        Optional<Item> itemToLoan = itemRepository.findItemByTitle("No Man's Land");

        if(!itemToLoan.isPresent()){
            throw new RuntimeException("Expected Book Not Found");
        }

        Item loanable1 = itemToLoan.get();

        Optional<Item> itemToLoan2 = itemRepository.findItemByTitle("Milenial Sky");

        if(!itemToLoan2.isPresent()){
            throw new RuntimeException("Expected Book Not Found");
        }

        Item loanable2 = itemToLoan2.get();

        Optional<Item> itemToLoan3 = itemRepository.findItemByTitle("In the wild");

        if(!itemToLoan3.isPresent()){
            throw new RuntimeException("Expected Book Not Found");
        }

        Item loanable3 = itemToLoan3.get();


        Role role1 = new Role();
        role1.setName("ADMIN");
        roleRepository.save(role1);
        Role role2 = new Role();
        role2.setName("EMPLOYEE");
        roleRepository.save(role2);


        User user1 = new User();
        user1.setFirstName("Tom");
        user1.setLastName("Jhonas");
        user1.setCity("Warsaw");
        user1.setEmail("tom.jhonas@hm.com");
        user1.setPhoneNumber("788-888-888");
        user1.setAddress("Uliczna 23/23");
        user1.setPostCode("00-001");
        user1.addLoan(new Loan(user1, itemToLoan.get()));

        users.add(user1);



        User user2 = new User();
        user2.setFirstName("Jason");
        user2.setLastName("Nuggat");
        user2.setCity("Warsaw");
        user2.setEmail("jason.nuggat@hm.com");
        user2.setPhoneNumber("222-388-588");
        user2.setAddress("Miejska 23/23");
        user2.setPostCode("00-001");
        Loan loan = new Loan(user2, itemToLoan3.get());
        loan.setBeforeDeadline(false);
        //loan.setLoanDeadline("12/10/2017");
        user2.addLoan(loan);

        users.add(user2);

        User user3 = new User();
        user3.setFirstName("Rob");
        user3.setLastName("Thompson");
        user3.setCity("Warsaw");
        user3.setEmail("rob.thompson@hm.com");
        user3.setPhoneNumber("111-555-877");
        user3.setAddress("Uliczna 23/23");
        user3.setPostCode("00-001");
        user3.addLoan(new Loan(user3, itemToLoan2.get()));
        users.add(user3);


        return users;

    }

}
