package com.nobrand.journal.springbootjournal.web;

import com.nobrand.journal.springbootjournal.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
* With @Controller annotation, Spring MVC engine treats this class as Controller.
* With @Autowired annotation, auto allocating object into the variable.
* */
@Controller
public class JournalController {

    @Autowired
    JournalRepository repo;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("journal", repo.findAll());

        // return "index" means that Spring engine try to find "index.html".
        return "index";
    }
}
