package blog;

import blog.Entity.Post;
import blog.Entity.User;
import blog.Repository.PostRepository;
import blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        // save a couple of users
        userRepository.save(new User("Jerome", "jerome@gmail.com", "jerome", "test.png"));
        userRepository.save(new User("Test", "test@gmail.com", "testtest", "test.png"));
        userRepository.save(new User("Dimitri", "dimitri@gmail.com", "dimitri", "test.png"));

        postRepository.deleteAll();

        List<String> tags = new ArrayList<String>();
        tags.add("Welcome");
        tags.add("Test");
        postRepository.save(new Post("Jerome", "Mon premier message", tags));
        postRepository.save(new Post("Jerome", "Mon deuxieme message", tags));
        postRepository.save(new Post("Dimitri", "Mon premier message", tags));
    }

}