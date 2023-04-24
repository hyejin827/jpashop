package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list")
    public List<Item> list() {
        List<Item> itemList = itemService.findItems();
        return itemList;
    }

    @PostMapping("/new")
    public String create(@RequestBody Book book) {
        itemService.saveItem(book);
        log.info("book => ", book);
        return "success";
    }

     @GetMapping("/detail/{itemId}")
     public Book detail(@PathVariable Long itemId){
        Item item = itemService.findOne(itemId);

        Book book = new Book();
        BeanUtils.copyProperties(item, book);

        log.info("item", item);
        log.info("book", book);
        return book;
     }
}
