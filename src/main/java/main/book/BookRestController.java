package main.book;

import main.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/add/{id}/{publisherId}")
    public void addBook(@PathVariable("id") long id ,@PathVariable("publisherId") long publisherId, @RequestBody Book book) {
        bookService.addBook(id,publisherId,book);
    }
    @PutMapping("/update/{id}/{publisherId}")
    public void updateBook(@PathVariable("id") long id ,@PathVariable("publisherId") long publisherId, @RequestBody Book book) {
        bookService.updateBook(id,publisherId,book);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
    @GetMapping("/countBooksPerPublisher/{id}")
    public double countBooksPerPublisher(@PathVariable long id) {
        return bookService.countBooksPerPublisher(id);
    }
    @GetMapping("/countAverageBooksPerPublisher")
    public Double countAverageBooksPerPublisher() {
        return bookService.countAverageBooksPerPublisher();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, org.hibernate.service.spi.ServiceException.class, ResponseStatusException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        else if (ex instanceof ServiceException) {
            errors.put(((ServiceException) ex).getAction(), ex.getMessage());
        }
        else {
            errors.put(((ResponseStatusException)ex).getReason(), ex.getCause().getMessage());
        }
        return errors;
    }



}
