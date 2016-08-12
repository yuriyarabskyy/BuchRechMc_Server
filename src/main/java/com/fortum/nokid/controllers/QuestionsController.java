package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.*;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
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
    @Transactional
    @ResponseBody
    public String answerQuestion(@RequestBody AnswerQuestionWrapper wrapper) {
        try {
            UserQuestion uq = new UserQuestion();
            User user = userDAO.findById(wrapper.getUser_id());
            Question question = questionDAO.findById(wrapper.getQuestion_id());
            uq.setUser(user);
            uq.setQuestion(question);
            uq.setGivenAnswer(answerDAO.findByQuestionAndAnswerId(question, wrapper.getAnswer_id()));
            uq.setTried(true);

            String s = "select * from users_questions" +
                    " where user_id = :user_id and question_id = :question_id";
            SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(s);
            query.addEntity(UserQuestion.class);
            query.setParameter("user_id", wrapper.getUser_id());
            query.setParameter("question_id", wrapper.getQuestion_id());
            List<UserQuestion> uqList = query.list();
            if (!uqList.isEmpty()) uq.setId(uqList.get(0).getId());

            if (question.getCorrectAnswerId() == wrapper.getAnswer_id())
                uq.setCorrectlyAnswered(true);
            else uq.setCorrectlyAnswered(false);
            uqDAO.save(uq);
        } catch (Exception e) {
            return "Something went wrong";
        }
        return "Success";
    }

    @CrossOrigin(origins = "*")
    @Transactional
    @RequestMapping(value = "/getAllTopics", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Object> getAllTopics() {
        String s = "select topic from questions q " +
                "group by chapter, topic order by chapter ASC";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(s);
        List<Object> list = query.list();
        return list;
    }

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private AnswerDAO answerDAO;

    @Autowired
    private UserQuestionDAO uqDAO;

    @Autowired
    private SessionFactory sessionFactory;

    public static class AnswerQuestionWrapper {
        private int user_id;
        private int question_id;
        private int answer_id;

        AnswerQuestionWrapper() { }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public int getAnswer_id() {
            return answer_id;
        }

        public void setAnswer_id(int answer_id) {
            this.answer_id = answer_id;
        }
    }

    public static class TopicWrapper {
        private int chapter;
        private String topic;

        TopicWrapper() { }

        public TopicWrapper(int chapter, String topic) {
            this.chapter = chapter;
            this.topic = topic;
        }

        public int getChapter() {
            return chapter;
        }

        public void setChapter(int chapter) {
            this.chapter = chapter;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }

}
