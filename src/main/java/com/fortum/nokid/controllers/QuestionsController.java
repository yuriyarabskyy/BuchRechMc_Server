package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.Question;
import com.fortum.nokid.entities.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/questions")
@RestController
public class QuestionsController {

    @RequestMapping(value = "/getDummies",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Question> getAllQuestions(){
        List<Question> questions=new ArrayList<>();
        questions.add(new Question("How are you?"));
        questions.add(new Question("What is your name?"));


        return questions;
    }

    @RequestMapping("/create/{content}")
    public String create(@PathVariable("content") String content){
        Question question;
        try{
            question=new Question(content);
            questionDAO.save(question);
        }catch (Exception ex){
            return "Error creating the question: " + ex.toString();
        }
        return "Question was succesfully created! (id = " + question.getId() + ")";
    }


    @RequestMapping(value ="/getByContent",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Question> getByContent(@RequestBody String content){
        List<Question> questions;
        try {
            questions = questionDAO.findByContentIgnoreCase(content);
        }
        catch (Exception ex) {
            questions = new LinkedList<>();
            questions.add(new Question(ex.toString()));
        }
        return questions;
    }

    @RequestMapping(value ="/getById", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Question> getById(@RequestBody String ids){

        List<Question> questions = new LinkedList<>();

        for (String id : ids.trim().split(",")) {

            try {
                Question question = questionDAO.findById(Long.parseLong(id));
                if (question != null) questions.add(question);
            }
            catch (Exception ex) {
                questions = new LinkedList<>();
                questions.add(new Question(ex.toString()));
                return questions;
            }

        }
        return questions;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Iterable<Question> getAll() {
        return questionDAO.findAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/pushQuestions", method = RequestMethod.POST)
    @ResponseBody
    public String pushQuestions(@RequestBody List<Question> questions) {

        try {
            questionDAO.save(questions);
        }
        catch(Exception ex) {
            return "Something went horribly wrong";
        }

        return "Successfully saved your questions";

    }

    /*
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    @ResponseBody
    public int getCount() {
        TODO custom behaviour to count
    }
    */

    @Autowired
    private QuestionDAO questionDAO;
}
