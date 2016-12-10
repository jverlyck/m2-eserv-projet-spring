package blog.Repository;

import blog.Entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    public List<Post> findByAuteur(String auteur);


}
