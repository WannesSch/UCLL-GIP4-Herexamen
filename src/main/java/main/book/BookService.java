package main.book;

import main.author.AuthorRepository;
import main.user.User;
import main.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UserRepository userRepository;

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(long id, long publisherId, Book book) {

        if (authorRepository.findById(id).isPresent()) {
            book.setAuthor(authorRepository.findById(id).get());

        if (userRepository.findById(publisherId).isPresent()) {
            book.setPublisher(userRepository.findById(publisherId).get());
            bookRepository.save(book);
        }
        }
    }

    public void updateBook(long id, long publisherId, Book book) {
        if (authorRepository.findById(id).isPresent()) {
            book.setAuthor(authorRepository.findById(id).get());
        }
        if (userRepository.findById(publisherId).isPresent() && userRepository.findById(publisherId).get().getRole().equals("PUBLISHER")) {
            book.setPublisher(userRepository.findById(publisherId).get());
        }
        bookRepository.save(book);
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public double countBooksPerPublisher(long id) {
        return bookRepository.countBooksPerPublisher(id);
    }

    public Double countAverageBooksPerPublisher() {
        double count = 0;
        ArrayList<Long> ids = new java.util.ArrayList<Long>() {
        };

        for (User user : userRepository.findAll()) {
            if (user.getRole().contains("PUBLISHER")) {
                ids.add(user.getId());
            }
        }
        for(long id : ids) {
            System.out.println(id);
            count += bookRepository.countBooksPerPublisher(id);
        }
        System.out.println(ids.size());
        System.out.println(count);
        return (count/ ids.size());
    }
}