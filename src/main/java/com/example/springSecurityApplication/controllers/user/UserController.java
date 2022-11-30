package com.example.springSecurityApplication.controllers.user;

import com.example.springSecurityApplication.enumm.Status;
import com.example.springSecurityApplication.models.Favourites;
import com.example.springSecurityApplication.models.Manuscript;
import com.example.springSecurityApplication.models.Order;
import com.example.springSecurityApplication.repositories.FavouritesRepository;
import com.example.springSecurityApplication.repositories.OrderRepository;
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

    private final OrderRepository orderRepository;
    private final FavouritesRepository favouritesRepository;

    private final ManuscriptService manuscriptService;

    public UserController(OrderRepository orderRepository, FavouritesRepository favouritesRepository, ManuscriptService manuscriptService) {
        this.orderRepository = orderRepository;
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
        if(role.equals("ROLE_ADMIN"))
        {
            return "redirect:/admin";
        }
        model.addAttribute("manuscripts", manuscriptService.getAllManuscript());
        return "user/index";
    }

    @GetMapping("/favourites/add/{id}")
    public String addManuscriptInFavourites(@PathVariable("id") int id, Model model){
        Manuscript manuscript = manuscriptService.getManuscriptId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        Favourites favourites = new Favourites(id_person, manuscript.getId());
        favouritesRepository.save(favourites);
        return "redirect:/favourites";
    }

    @GetMapping("/favourites")
    public String favourites(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Favourites> favouritesList = favouritesRepository.findByPersonId(id_person);
        List<Manuscript> manuscriptsList = new ArrayList<>();
        for (Favourites favourites : favouritesList) {
            manuscriptsList.add(manuscriptService.getManuscriptId(favourites.getManuscriptId()));
        }

        float price = 0;
        for (Manuscript manuscript : manuscriptsList) {
            price += manuscript.getPrice();
        }
        model.addAttribute("price", price);
        model.addAttribute("favourites_manuscript", manuscriptsList);
        return "user/favourites";
    }

    @GetMapping("/favourites/delete/{id}")
    public String deleteManuscriptFavourites(Model model, @PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        favouritesRepository.deleteFavouritesByManuscriptId(id);
        return "redirect:/favourites";
    }

    @GetMapping("/order/create")
    public String order(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Favourites> favouritesList = favouritesRepository.findByPersonId(id_person);
        List<Manuscript> manuscriptsList = new ArrayList<>();
        // Получаем продукты из корзины по id
        for (Favourites favourites : favouritesList) {
            manuscriptsList.add(manuscriptService.getManuscriptId(favourites.getManuscriptId()));
        }

        float price = 0;
        for (Manuscript manuscript : manuscriptsList){
            price += manuscript.getPrice();
        }

        String uuid = UUID.randomUUID().toString();
        for (Manuscript manuscript : manuscriptsList){
            Order newOrder = new Order(uuid, manuscript, personDetails.getPerson(), 1, manuscript.getPrice(), Status.Получен);
            orderRepository.save(newOrder);
            favouritesRepository.deleteFavouritesByManuscriptId(manuscript.getId());
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String ordersUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Order> orderList = orderRepository.findByPerson(personDetails.getPerson());
        model.addAttribute("orders", orderList);
        return "/user/orders";
    }
}