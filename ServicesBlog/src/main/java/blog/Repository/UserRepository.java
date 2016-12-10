package blog.Repository;

import blog.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
}
