package blog.Form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentaireForm {

    @NotNull
    @Size(min=1, max=150)
    private String commentaire;


    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "commentaire: " + this.commentaire  + ")";
    }
}