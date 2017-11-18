package matmic.librarymaneger.controllers;

import matmic.librarymaneger.commands.BookCommand;
import matmic.librarymaneger.commands.BookLoanCommand;
import matmic.librarymaneger.commands.LibraryAccountCommand;
import matmic.librarymaneger.commands.UserCommand;
import matmic.librarymaneger.model.BookLoan;
import matmic.librarymaneger.services.BookLoanService;
import matmic.librarymaneger.services.BookService;
import matmic.librarymaneger.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final BookService bookService;
    private final BookLoanService bookLoanService;

    public UserController(UserService userService, BookService bookService, BookLoanService bookLoanService) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookLoanService = bookLoanService;
    }

    @RequestMapping("userpanel/showlist")
    public String showUsersPanel(Model model){
        model.addAttribute("users", userService.getUsers());
        return "userpanel/userslist";
    }

    @RequestMapping("userpanel/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findById(new Long(id)));
        return "userpanel/show";
    }

    @RequestMapping("userpanel/newuser")
    public String newUser(Model model){
        model.addAttribute("user", new UserCommand());

        return "userpanel/userform";
    }

    @PostMapping
    @RequestMapping("userpanel")
    public String saveOrUpdate(@ModelAttribute UserCommand command){
        System.out.println("newUser userCmd ID" + command.getId());
        UserCommand savedUserCommand = userService.saveUserCommand(command);

        System.out.println(savedUserCommand.getId() + " saved user id");
        return "redirect:/userpanel/" + savedUserCommand.getId() + "/show";
    }


    @RequestMapping("userpanel/{id}/userupdate")
    public String updateUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findCommandById(Long.valueOf(id)));
        return "userpanel/userform";
    }

    @GetMapping("userpanel/{id}/delete")
    public String deleteUserById(@PathVariable String id){
        userService.deleteById(Long.valueOf(id));
        return "index";

    }

    @RequestMapping("userpanel/{id}/newloan")
    public String searchForItemToBorrow(@PathVariable String id, Model model){
        System.out.println("hi");
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("user", userService.findById(Long.valueOf(id)));
        System.out.println("hi1");
        return "userpanel/itemlist";
    }

    @GetMapping("userpanel/{id}/book/{bookId}/newloan")
    public String showLoanForm(@PathVariable String id, @PathVariable String bookId, Model model){
        System.out.println(userService.findById(Long.valueOf(id)).getUserLibraryAccount().getBookLoans().size());


        model.addAttribute("user", userService.findById(Long.valueOf(id)));
        model.addAttribute("book", bookService.findById(Long.valueOf(bookId)));

        //System.out.println(.size());
        for(BookLoan bL : userService.findById(Long.valueOf(id)).getUserLibraryAccount().getBookLoans()){
            System.out.println(bL.getId() + " " + bL.getLibraryAccount().getId());
        }
        return "userpanel/newloanform";
    }

    @RequestMapping("userpanel/{id}/book/{bookId}/saveloan")
    public String saveLoan(@PathVariable String id, @PathVariable String bookId){
        BookCommand bookCommand = bookService.findCommandById(Long.valueOf(bookId));
        LibraryAccountCommand userAccountCommand = userService.findCommandById(Long.valueOf(id)).getLibraryAccountCommand();
        BookLoanCommand bookLoanCommand = new BookLoanCommand();
        bookLoanCommand.setBook(bookCommand);
        bookLoanCommand.setLibraryAccountId(userAccountCommand.getId());

         BookLoanCommand savedBookLoan = bookLoanService.saveBookLoanCommand(bookLoanCommand);
//        UserCommand userCommand = userService.findCommandById(Long.valueOf(userId));
//        BookCommand bookCommand = bookService.findCommandById(Long.valueOf(bookId));
//        BookLoanCommand newLoan = new BookLoanCommand();
        System.out.println(savedBookLoan.getId());
//        BookLoanCommand newLoan = userService.defineAndSaveNewLoan(userCommand.getLibraryAccountCommand(), bookCommand);
        return "redirect:/userpanel/" + id + "/show";
    }


    @RequestMapping("mainpanel")
    public String goToMain(){
        return "index";
    }




}
