package blog;

import blog.Entity.Post;
import blog.Entity.User;
import blog.Form.EditProfilForm;
import blog.Form.InscriptionForm;
import blog.Form.LoginForm;
import blog.Form.CommentaireForm;
import blog.storage.StorageFileNotFoundException;
import blog.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogController {

    private User user;
    private final StorageService storageService;

    @Autowired
    public BlogController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        Post[] posts = restTemplate.getForObject("http://localhost:8000/api/posts", Post[].class);

        model.addAttribute("posts", posts);

        return "accueil";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(LoginForm loginForm) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkLogin(@Valid LoginForm loginForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        RestTemplate restTemplate = new RestTemplate();
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        User user = restTemplate.getForObject("http://localhost:8000/api/users/checkLogin/" + username + "/" + password , User.class);

        if(user == null){
            return "login";
        }

        this.user = user;

        return "redirect:/";
    }

    @RequestMapping(value = "/inscription", method = RequestMethod.GET)
    public String showInscription(InscriptionForm inscriptionForm) {
        return "inscription";
    }

    @RequestMapping(value = "/inscription", method = RequestMethod.POST)
    public String checkInscription(@Valid InscriptionForm inscriptionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "inscription";
        }

        storageService.store(inscriptionForm.getAvatar());

        RestTemplate restTemplate = new RestTemplate();
        User u = new User(
            inscriptionForm.getUsername(),
            inscriptionForm.getEmail(),
            inscriptionForm.getPassword(),
            inscriptionForm.getAvatar().getOriginalFilename()
        );

        User user = restTemplate.postForObject("http://localhost:8000/api/users/", u, User.class);

        return "redirect:/";
    }

    @RequestMapping(value = "/profil/{name}", method = RequestMethod.GET)
    public String profil(@PathVariable String name, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        User user = restTemplate.getForObject("http://localhost:8000/api/users/" + name, User.class);
        Post[] posts = restTemplate.getForObject("http://localhost:8000/api/posts/list/" + name, Post[].class);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);

        return "profil";
    }

    @RequestMapping(value = "/hashtag/{tag}", method = RequestMethod.GET)
    public String hashtag(@PathVariable String tag, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        Post[] postI = restTemplate.getForObject("http://localhost:8000/api/posts", Post[].class);


        int p =0;
        for(int i =0;i<postI.length;i++)
        {
           if(postI[i].getTags().contains(tag)){
               p++;
           }

        }
        Post[] posts = new Post[p];
        p =0;
        for(int i =0;i<postI.length;i++)
        {
            if(postI[i].getTags().contains(tag)){
                posts[p]=postI[i];
                p++;
            }

        }        model.addAttribute("posts", posts);

        return "postHashtag";
    }

    @RequestMapping(value = "/commentaire", method = RequestMethod.GET)
    public String showCommentaire(CommentaireForm commentaireForm) {
        return "commentaire";
    }

    @RequestMapping(value = "/commentaire", method = RequestMethod.POST)
    public String checkCommentaire(@Valid CommentaireForm commentaireForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "commentaire";
        }
        List<String> tags = new ArrayList<String>();
        String tag = "";
        int j = 1;
        String message = commentaireForm.getCommentaire()+" f";
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '#') {
                while ((message.charAt(i + j) != ' ') || ((i + j) == message.length())) {
                    tag += message.charAt(i + j);
                    j++;
                }
                j = 1;
                tags.add(tag);
                tag = "";
            }
        }
        RestTemplate restTemplate = new RestTemplate();
        Post p = new Post(
                this.user.getUsername(),
                this.user.getAvatar(),
                commentaireForm.getCommentaire() + tag,
                tags
        );

        Post post = restTemplate.postForObject("http://localhost:8000/api/posts/", p, Post.class);

        return "redirect:/";
    }

    @RequestMapping(value = "/profil-edit", method = RequestMethod.GET)
    public String showEditProfil(EditProfilForm editProfilForm, Model model) {
        if(this.user == null) {
            return "redirect:login";
        }

        model.addAttribute("username", this.user.getUsername());
        model.addAttribute("email", this.user.getEmail());

        return "editProfil";
    }

    @RequestMapping(value = "/profil-edit", method = RequestMethod.POST)
    public String editProfil(@Valid EditProfilForm editProfilForm, BindingResult bindingResult, Model model) {
        if(this.user == null) {
            return "redirect:login";
        }

        System.out.println(bindingResult.getModel());

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", true);
            model.addAttribute("username", this.user.getUsername());
            model.addAttribute("email", editProfilForm.getEmail());
            return "editProfil";
        }

        this.storageService.store(editProfilForm.getAvatar());

        RestTemplate restTemplate = new RestTemplate();
        User u = new User(
                this.user.getUsername(),
                editProfilForm.getEmail(),
                this.user.getPassword(),
                editProfilForm.getAvatar().getOriginalFilename()
        );

        restTemplate.put("http://localhost:8000/api/users/" + this.user.getId(), u, User.class);

        this.user = u;

        return "redirect:profil/" + this.user.getUsername();

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}