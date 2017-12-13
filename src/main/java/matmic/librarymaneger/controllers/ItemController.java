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

    @RequestMapping("itempanel/itemslist")
    public String showItemsList (Model model){
        model.addAttribute("items", itemService.getItems());
        return "itempanel/itemslist";
    }

    @GetMapping("itempanel/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findById(new Long(id)));
        return "itempanel/show";
    }

    @GetMapping("itempanel/newitem")
    public String newItem(Model model){
        model.addAttribute("item", new Item());
        return "itempanel/itemform";
    }

    @PostMapping("itempanel")
    public String saveOrUpdate(@ModelAttribute Item item){
        Item savedItem = itemService.saveItem(item);
        return "redirect:/itempanel/" + savedItem.getId() + "/show";
    }

    @GetMapping("itempanel/{id}/itemupdate")
    public String updateItem(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findItemById(Long.valueOf(id)));
        return "itempanel/itemform";
    }

    @GetMapping("itempanel/{id}/delete")
    public String deleteItemById(@PathVariable String id){
        itemService.deleteById(Long.valueOf(id));
        return "redirect:/itempanel/itemslist";
    }

}
