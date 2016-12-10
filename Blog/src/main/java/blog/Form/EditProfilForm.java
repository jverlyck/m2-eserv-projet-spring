package blog.Form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditProfilForm {

    @NotNull
    @Size(min=1, max=30)
    private String email;

    private String avatar;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
