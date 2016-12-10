package blog.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String auteur;
    private String message;

    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    public Post() {}

    public Post(String auteur, String message, List<String> tags) {
        this.auteur = auteur;
        this.message = message;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
