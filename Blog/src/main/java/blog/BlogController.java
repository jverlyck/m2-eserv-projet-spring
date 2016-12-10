package blog;

import blog.Entity.Post;
import blog.Entity.User;
import blog.Form.InscriptionForm;
import blog.Form.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Controller
public class BlogController {

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

        RestTemplate restTemplate = new RestTemplate();
        User u = new User(
            inscriptionForm.getUsername(),
            inscriptionForm.getEmail(),
            inscriptionForm.getPassword(),
            inscriptionForm.getAvatar()
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
}