package com.spring.qa.autoGb.lesson2.example;


import com.spring.qa.autoGb.lesson2.example.dto.Book;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class CrudOperations {

    Map<Integer, Book> data = new HashMap<>();

    @PostConstruct
    void init(){
        data.put(1,new Book(1,"Two captains","Kaverin",1100));
        data.put(2,new Book(2,"Captain's daughter","Pushkin",900));
        data.put(3,new Book(3,"War and peace","Tolstoy",5000));
        data.put(4,new Book(4,"The witcher","Sapkovsky",700));
        data.put(5,new Book(5,"Game of thrones","Martin",900));
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable int id) {
        return data.get(id);
    }

    @GetMapping("/all")
    public List<Book> get() {
        return new ArrayList<>(data.values());
    }

    @PostMapping()
    public void save(@RequestBody Book books){
        int id = data.size() + 1;
        books.setId(id);
        data.put(id,books);
    }

    @PutMapping("/{id}")
    public void change(@PathVariable int id, @RequestBody Book userChanging) {
        Book book = data.get(id);
        book.setBookName(userChanging.getBookName());
        book.setAuthor(userChanging.getAuthor());
        book.setCountOfPages(userChanging.getCountOfPages());
        data.put(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        data.remove(id);
    }
}
