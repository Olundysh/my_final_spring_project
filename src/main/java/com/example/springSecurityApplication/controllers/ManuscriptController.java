package com.example.springSecurityApplication.controllers;

import com.example.springSecurityApplication.repositories.ManuscriptRepository;
import com.example.springSecurityApplication.services.ManuscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manuscript")
public class ManuscriptController {

    private final ManuscriptRepository manuscriptRepository;
    private final ManuscriptService manuscriptService;

    @Autowired
    public ManuscriptController(ManuscriptRepository manuscriptRepository, ManuscriptService manuscriptService) {
        this.manuscriptRepository = manuscriptRepository;
        this.manuscriptService = manuscriptService;
    }

    @GetMapping("")
    public String getAllManuscript(Model model) {
        model.addAttribute("manuscripts", manuscriptService.getAllManuscript());
        return "manuscript";
    }

    @GetMapping("/info/{id}")
    public String infoUser(@PathVariable("id") int id, Model model){
        model.addAttribute("manuscript", manuscriptService.getManuscriptId(id));
        return "infoManuscript";
    }

    @PostMapping("/search")
    public String manuscriptSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "")String price, @RequestParam(value = "contact", required = false, defaultValue = "")String contact, Model model){
        if(!ot.isEmpty() & !Do.isEmpty()){
            if(!price.isEmpty()){
                if(price.equals("sorted_by_ascending_price")){
                    if(!contact.isEmpty())
                    {
                        if(contact.equals("furniture")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        } else if(contact.equals("appliances")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        }else if(contact.equals("clothes")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        }
                    }
                }
                else if (price.equals("sorted_by_descending_price")){
                    if(!contact.isEmpty())
                    {
                        if(contact.equals("furniture")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        } else if(contact.equals("appliances")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        }else if(contact.equals("clothes")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        }
                    }
                }
            } else {
                model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search, Float.parseFloat(ot), Float.parseFloat(Do)));

            }
        }
        else {
            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleContainingIgnoreCase(search));
        }

        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        model.addAttribute("manuscripts", manuscriptService.getAllManuscript());
        return "manuscript";

    }

}
