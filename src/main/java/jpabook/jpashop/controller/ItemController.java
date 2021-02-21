package jpabook.jpashop.controller;

import jpabook.jpashop.Service.ItemService;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        //setter를 만들지 말고, 값들을 한꺼번에 세팅할 수 있는 함수를 만드는 것이 좋다
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsdn(form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId); //예제를 단순하게 하기 위해서 casting 하였다

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsdn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form) {
/*
        Book book = new Book();
        //book을 새로만들었지만 id를 부여했으므로, 이 book은 db를 거져간 book이다. -> 준영속 객체
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsdn(form.getIsbn());
        //준영속 객체 (영속성 컨텍스트가 더는 관리하지 않는 객체. JPA가 관리하지 않는다) 이므로 Entitiy의 값을 바꾼다고 db에 반영되지 않는다
        //itemService의 saveItem함수에서 merge를 해줘야한다
        itemService.saveItem(book);
*/
        //merge를 사용하지 않고 변경감지를 사용할 경우
        //값이 너무 많다면 Dto를 만들어서 사용하자
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}
