package matmic.librarymaneger.controllers;


import matmic.librarymaneger.command.ItemCommand;
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

    @RequestMapping("item/list")
    public String showItemsList (Model model){
        model.addAttribute("items", itemService.getItems());
        return "item/itemlist";
    }

    @GetMapping("item/{id}/display")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findById(new Long(id)));
        return "item/itemdisplay";
    }

    @GetMapping("item/newitem")
    public String newItem(Model model){
        model.addAttribute("item", new Item());
        return "item/newitem";
    }

    @PostMapping("saveitem")
    public String saveOrUpdate(@ModelAttribute("item") ItemCommand itemCommand){
        ItemCommand savedItem = itemService.saveItem(itemCommand);
        return "redirect:/item/" + savedItem.getId() + "/display";
    }

    @GetMapping("item/{id}/update/details")
    public String updateItem(@PathVariable String id, Model model){
        model.addAttribute("item", itemService.findItemCommandById(Long.valueOf(id)));
        return "item/itemform";
    }

    @GetMapping("item/{id}/delete")
    public String deleteItemById(@PathVariable String id){
        itemService.deleteById(Long.valueOf(id));
        return "redirect:/item/itemlist";
    }

}
