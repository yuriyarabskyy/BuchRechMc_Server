package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.Question;
import com.fortum.nokid.entities.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/questions")
@RestController
public class QuestionsController {




    @RequestMapping(value = "/getDummies",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Question> getAllQuestions(){
        List<Question> questions=new ArrayList<>();
        questions.add(new Question("How are you?"));
        questions.add(new Question("What is your name?"));
        questions.add(new Question("ÄÖÜ äöü"));

        return questions;
    }

    @RequestMapping("/create")
    public String create(String content){
        Question question;
        try{
            question=new Question(content);
            questionDAO.save(question);
        }catch (Exception ex){
            return "Error creating the user: " + ex.toString();
        }
        return "Question was succesfully created! (id = " + question.getId() + ")";
    }


    @RequestMapping(value ="/get-by-content",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Question getByContent(String content){
        Question question;
        try {
            question = questionDAO.findByContentIgnoreCase(content);
        }
        catch (Exception ex) {
            return new Question(ex.toString());
        }
        return question;
    }

    @Autowired
    private QuestionDAO questionDAO;
}
