package blog.Controller;

import blog.Entity.Post;
import blog.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository repository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Post> getPosts() {
        return this.repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable Long id) {
        return this.repository.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Post postPost(@RequestBody Post post) {
        return this.repository.save(post);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Post putPost(@PathVariable Long id, @RequestBody Post post) {
        Post p = this.repository.findOne(id);

        p.setMessage(post.getMessage());
        p.setTags(post.getTags());

        return this.repository.save(p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable Long id) {
        this.repository.delete(id);
    }
}
