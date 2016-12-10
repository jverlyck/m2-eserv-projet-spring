package blog;

import blog.Entity.User;
import blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        // save a couple of users
        repository.save(new User("Jerome", "jerome@gmail.com", "jerome", "test.png"));
        repository.save(new User("Test", "test@gmail.com", "testtest", "test.png"));
        repository.save(new User("Dimitri", "dimitri@gmail.com", "dimitri", "test.png"));
    }

}