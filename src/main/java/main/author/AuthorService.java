package main.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }
    public void deleteAuthor(long id) {
        authorRepository.deleteById(id);
    }

}
