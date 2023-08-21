package main.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<Integer> countPublishers() {
        int count = 0;
        for(User user : userRepository.findAll()) {
            if(user.getRole().contains("PUBLISHER")) {
                count++;
            }
        }
        return Optional.of(count);
    }
    public void addUser(User user) {
        userRepository.save(user);
    }
    public void updateUser(User user) {
        userRepository.save(user);
    }
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


}
