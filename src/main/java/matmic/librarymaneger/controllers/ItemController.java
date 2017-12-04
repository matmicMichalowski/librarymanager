package matmic.librarymaneger.controllers;


import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.services.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("item/showitems")
    public String showItemsList (Model model){
        model.addAttribute("item", itemService.getItems());
        return "itempanel/itemslist";
    }

    @GetMapping("items/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findById(new Long(id)));
        return "items/show";
    }

    @GetMapping("items/newitem")
    public String newItem(Model model){
        model.addAttribute("item", new Item());
        return "itempanel/itemform";
    }

    @PostMapping("items")
    public String saveOrUpdate(@ModelAttribute Item item){
        Item savedItemCommand = itemService.saveItem(item);
        return "redirect:/items/" + savedItemCommand.getId() + "/show";
    }

    @GetMapping
    public String updateItem(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findItemById(Long.valueOf(id)));
        return "item/itemform";
    }

    @GetMapping("items/{id}/delete")
    public String deleteItemById(@PathVariable String id){
        itemService.deleteById(Long.valueOf(id));
        return "items/list";
    }

}
