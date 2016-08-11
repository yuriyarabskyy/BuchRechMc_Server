package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/questions")
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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/answerQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String answerQuestion(@RequestBody AnswerQuestionWrapper wrapper) {
        try {
            UserQuestion uq = new UserQuestion();
            User user = userDAO.findById(wrapper.user_id);
            Question question = questionDAO.findById(wrapper.question_id);
            uq.setUser(user);
            uq.setQuestion(question);
            uq.setGivenAnswer(answerDAO.findByQuestionAndAnswerId(question, wrapper.answer_id));
            uq.setTried(true);
            if (question.getCorrectAnswerId() == wrapper.answer_id)
                uq.setCorrectlyAnswered(true);
            else uq.setCorrectlyAnswered(false);
            uqDAO.save(uq);
        } catch (Exception e) {
            return "Something went wrong";
        }
        return "Success";
    }


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private AnswerDAO answerDAO;

    @Autowired
    private UserQuestionDAO uqDAO;

    public static class AnswerQuestionWrapper {
        int user_id;
        int question_id;
        int answer_id;
    }

}
