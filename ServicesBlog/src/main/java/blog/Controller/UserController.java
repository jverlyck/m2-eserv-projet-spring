package blog.Controller;

import blog.Entity.User;
import blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        return this.repository.findAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String name) {
        return this.repository.findByUsername(name);
    }

    @RequestMapping(value = "/checkLogin/{username}/{password}", method = RequestMethod.GET)
    public User getUserByUsernameAndPassword(@PathVariable String username, @PathVariable String password) {
        return this.repository.findByUsernameAndPassword(username, password);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User postUser(@RequestBody User user) {
        return this.repository.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User putUser(@PathVariable Long id, @RequestBody User user) {
        User u = this.repository.findOne(id);

        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setAvatar(user.getAvatar());

        return repository.save(u);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {
        this.repository.delete(id);
    }
}
