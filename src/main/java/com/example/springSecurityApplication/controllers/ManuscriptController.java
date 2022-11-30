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
    public String manuscriptSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "dating", required = false, defaultValue = "")String dating, @RequestParam(value = "contact", required = false, defaultValue = "")String contact, Model model){
        if(!ot.isEmpty() & !Do.isEmpty()){
            if(!dating.isEmpty()){
                if(dating.equals("sorted_by_ascending_dating")){
                    if(!contact.isEmpty())
                    {
                        if(contact.equals("furniture")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByDatingAsc(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(Do), 1));
                        } else if(contact.equals("appliances")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByDatingAsc(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(Do), 2));
                        }else if(contact.equals("clothes")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByDatingAsc(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(Do), 3));
                        }
                    }
                }
                else if (dating.equals("sorted_by_descending_dating")){
                    if(!contact.isEmpty())
                    {
                        if(contact.equals("furniture")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByDatingDesc(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(Do), 1));
                        } else if(contact.equals("appliances")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByDatingDesc(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(Do), 2));
                        }else if(contact.equals("clothes")){
                            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndCategoryOrderByDatingDesc(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(Do), 3));
                        }
                    }
                }
            } else {
                model.addAttribute("search_manuscript", manuscriptRepository.findByTitleAndDatingGreaterThanEqualAndDatingLessThanEqual(search, Integer.parseInt(ot), Integer.parseInt(Do)));

            }
        }
        else {
            model.addAttribute("search_manuscript", manuscriptRepository.findByTitleContainingIgnoreCase(search));
        }

        model.addAttribute("value_search", search);
        model.addAttribute("value_dating_ot", ot);
        model.addAttribute("value_dating_do", Do);
        model.addAttribute("manuscripts", manuscriptService.getAllManuscript());
        return "manuscript";

    }

}
