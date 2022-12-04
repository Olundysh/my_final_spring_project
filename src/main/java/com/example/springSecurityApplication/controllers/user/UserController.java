package com.example.springSecurityApplication.controllers.user;

import com.example.springSecurityApplication.enumm.Status;
import com.example.springSecurityApplication.models.Favourites;
import com.example.springSecurityApplication.models.Manuscript;
import com.example.springSecurityApplication.models.Selection;
import com.example.springSecurityApplication.repositories.FavouritesRepository;
import com.example.springSecurityApplication.repositories.SelectionRepository;
import com.example.springSecurityApplication.security.PersonDetails;
import com.example.springSecurityApplication.services.ManuscriptService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private final SelectionRepository selectionRepository;
    private final FavouritesRepository favouritesRepository;
    private final ManuscriptService manuscriptService;

    public UserController(SelectionRepository selectionRepository, FavouritesRepository favouritesRepository, ManuscriptService manuscriptService) {
        this.selectionRepository = selectionRepository;
        this.favouritesRepository = favouritesRepository;
        this.manuscriptService = manuscriptService;
    }

    @GetMapping("/index")
    public String index(Model model) {

        // Получаем объект аутентификации - > с помощью Spring SecurityContextHolder обращаемся к контексту и на нем вызываем метод аутентификации.
        // Из потока для текущего пользователя мы получаем объект, который был положен в сессию после аутентификации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        String role = personDetails.getPerson().getRole();
        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        model.addAttribute("manuscripts", manuscriptService.getAllManuscript());
        return "user/index";
    }

    @GetMapping("/favourites/add/{id}")
    public String addManuscriptInFavourites(@PathVariable("id") int id, Model model) {
        Manuscript manuscript = manuscriptService.getManuscriptId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        Favourites favourites = new Favourites(id_person, manuscript.getId());
        favouritesRepository.save(favourites);
        return "redirect:/favourites";
    }

    @GetMapping("/favourites")
    public String favourites(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Favourites> favouritesList = favouritesRepository.findByPersonId(id_person);
        List<Manuscript> manuscriptsList = new ArrayList<>();
        for (Favourites favourites : favouritesList) {
            manuscriptsList.add(manuscriptService.getManuscriptId(favourites.getManuscriptId()));
        }

        float dating = 0;
        for (Manuscript manuscript : manuscriptsList) {
            dating += manuscript.getDating();
        }
        model.addAttribute("dating", dating);
        model.addAttribute("favourites_manuscript", manuscriptsList);
        return "user/favourites";
    }

    @GetMapping("/favourites/delete/{id}")
    public String deleteManuscriptFavourites(Model model, @PathVariable("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        favouritesRepository.deleteFavouritesByManuscriptId(id);
        return "redirect:/favourites";
    }

    @GetMapping("/selection/create")
    public String selection() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Favourites> favouritesList = favouritesRepository.findByPersonId(id_person);
        List<Manuscript> manuscriptsList = new ArrayList<>();
        // Получаем продукты из favourites по id
        for (Favourites favourites : favouritesList) {
            manuscriptsList.add(manuscriptService.getManuscriptId(favourites.getManuscriptId()));
        }


        String uuid = UUID.randomUUID().toString();
        for (Manuscript manuscript : manuscriptsList) {
            Selection newSelection = new Selection(uuid, manuscript, personDetails.getPerson(), manuscript.getDating(), Status.Order_accepted);
            selectionRepository.save(newSelection);
            favouritesRepository.deleteFavouritesByManuscriptId(manuscript.getId());
        }
        return "redirect:/selection";
    }

    @GetMapping("/selection")
    public String selectionUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Selection> selectionList = selectionRepository.findByPerson(personDetails.getPerson());
        model.addAttribute("selection", selectionList);
        return "/user/selection";
    }
}