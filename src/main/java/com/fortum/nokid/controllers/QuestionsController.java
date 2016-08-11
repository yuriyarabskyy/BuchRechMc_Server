package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.Answer;
import com.fortum.nokid.entities.AnswerDAO;
import com.fortum.nokid.entities.Question;
import com.fortum.nokid.entities.QuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/questions")
@RestController
public class QuestionsController {

    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
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
        List<Question> questions = new ArrayList<>();
        Iterable<Question> iterable = questionDAO.findAll();
        iterable.forEach(question -> questions.add(question));
        Collections.sort(questions, (Question q1, Question q2) -> q1.getFromPage() - q2.getFromPage());
        return questions;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/pushQuestions", method = RequestMethod.POST)
    @ResponseBody
    public String pushQuestions(@RequestBody List<Question> questions) {

        try {
            questionDAO.save(questions);
            for (Question q : questions) {
                for (Answer a : q.getPossibleAnswers()) {
                    a.setQuestion(q);
                    answerDAO.save(a);
                }
            }
        }
        catch(Exception ex) {
            return "Something went horribly wrong";
        }

        return "Successfully saved your questions";

    }

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private AnswerDAO answerDAO;
}
