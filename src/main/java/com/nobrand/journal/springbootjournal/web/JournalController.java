package com.nobrand.journal.springbootjournal.web;

import com.nobrand.journal.springbootjournal.domain.Journal;
import com.nobrand.journal.springbootjournal.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    /*
    * Return value is application/json;charset=UTF-8
    * With @ResponseBody annotation, with return value generate response body.
    *
    * Request URL: http://localhost:8080/journal
    * Request Method: GET
    * Status Code: 200
    * Content-Type: application/json;charset=UTF-8
    * [
    *   {"id":1,"title":"Start Spring Boot","created":"2021-07-07T15:00:00.000+00:00","summary":...},
    *   {"id":2,"title":"Make Spring Boot Project","created":"2021-07-08T15:00:00.000+00:00","summary":...},
    *   ...
    * ]
    * */
    @RequestMapping(value="/journal", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody List<Journal> getJournal() {
        return repo.findAll();
    }
}
