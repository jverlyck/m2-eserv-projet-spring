package blog;

import blog.Form.InscriptionForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class BlogController {

    @RequestMapping("/")
    public String acceuil(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("path",System.getProperty("user.dir"));
        return "acceuil";
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

        return "redirect:/";
    }
}