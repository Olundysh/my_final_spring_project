package com.example.springSecurityApplication.controllers.admin;

import com.example.springSecurityApplication.models.Image;
import com.example.springSecurityApplication.models.Manuscript;
import com.example.springSecurityApplication.repositories.CategoryRepository;
import com.example.springSecurityApplication.services.ManuscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ManuscriptService manuscriptService;

    private final CategoryRepository categoryRepository;

    @Autowired
    public AdminController(ManuscriptService manuscriptService, CategoryRepository categoryRepository) {
        this.manuscriptService = manuscriptService;
        this.categoryRepository = categoryRepository;
    }

    // Метод по отображению страницы админа:
    @GetMapping("")
    public String admin(Model model) {
        model.addAttribute("manuscripts", manuscriptService.getAllManuscript());
        return "admin/admin";
    }

    // Метод по отображению страницы с возможностью добавления рукописей:
    @GetMapping("/manuscript/add")
    public String addManuscript(Model model) {
        model.addAttribute("manuscript", new Manuscript());
        model.addAttribute("category", categoryRepository.findAll());
        return "manuscript/addManuscript";
    }

    // Метод по добавлению рукописи в БД через сервис->репозиторий (с одной фотографией; добавление еще 4х фотографий закомменчено за ненадобностью.
    @PostMapping("/manuscript/add")
    public String addManuscript(@ModelAttribute("manuscript") @Valid Manuscript manuscript, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one
//                                @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three, @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five") MultipartFile file_five
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "manuscript/addManuscript";
        }

        if (file_one != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setManuscript(manuscript);
            image.setFileName(resultFileName);
            manuscript.addImageToManuscript(image);
        }

//        if(file_two != null)
//        {
//            File uploadDir = new File(uploadPath);
//            if(!uploadDir.exists()){
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
//            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setManuscript(manuscript);
//            image.setFileName(resultFileName);
//            manuscript.addImageToManuscript(image);
//        }
//
//        if(file_three != null)
//        {
//            File uploadDir = new File(uploadPath);
//            if(!uploadDir.exists()){
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
//            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setManuscript(manuscript);
//            image.setFileName(resultFileName);
//            manuscript.addImageToManuscript(image);
//        }
//
//        if(file_four != null)
//        {
//            File uploadDir = new File(uploadPath);
//            if(!uploadDir.exists()){
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
//            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setManuscript(manuscript);
//            image.setFileName(resultFileName);
//            manuscript.addImageToManuscript(image);
//        }
//
//        if(file_five != null)
//        {
//            File uploadDir = new File(uploadPath);
//            if(!uploadDir.exists()){
//                uploadDir.mkdir();
//            }
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
//            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setManuscript(manuscript);
//            image.setFileName(resultFileName);
//            manuscript.addImageToManuscript(image);
//        }
//
        manuscriptService.saveManuscript(manuscript);
        return "redirect:/admin";
    }

    // Метод по удалению рукописи:
    @GetMapping("/manuscript/delete/{id}")
    public String deleteManuscript(@PathVariable("id") int id) {
        manuscriptService.deleteManuscript(id);
        return "redirect:/admin";
    }

    // Метод по отображению страницы с возможностью редактирования рукописи:
    @GetMapping("/manuscript/edit/{id}")
    public String editManuscript(Model model, @PathVariable("id") int id) {
        model.addAttribute("manuscript", manuscriptService.getManuscriptId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "manuscript/editManuscript";
    }

    // Метод для отправки формы с отредактированной рукописью:
    @PostMapping("/manuscript/edit/{id}")
    public String editManuscript(@ModelAttribute("manuscript") @Valid Manuscript manuscript, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "manuscript/editManuscript";
        }
        manuscriptService.updateManuscript(id, manuscript);
        return "redirect:/admin";
    }
}
