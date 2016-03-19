package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.Question;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/questions")
@RestController
public class QuestionsController {
    @RequestMapping(value = "/getAll",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Question> getAllQuestions(){
        List<Question> questions=new ArrayList<>();
        questions.add(new Question("How are you?"));
        questions.add(new Question("What is your name?"));
        questions.add(new Question("ÄÖÜ äöü"));

        return questions;
    }

}
