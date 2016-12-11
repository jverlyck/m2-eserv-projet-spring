package blog.Entity;

import java.util.List;

public class Post {

    private String auteur;
    private String avatar;
    private String message;
    private List<String> tags;

    public Post() {}

    public Post(String auteur,String avatar, String message,List<String> tags){
        this.auteur = auteur;
        this.avatar = avatar;
        this.message = message;
        this.tags = tags;
    }
    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
