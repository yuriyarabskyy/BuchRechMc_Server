package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.*;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/questions")
@RestController
public class QuestionsController {

    @RequestMapping(value ="/getById", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getById(@RequestParam("id") long id){

        Question question = questionDAO.findById(id);

        if (question != null) {
            return ResponseEntity.ok(questionDAO.findById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Iterable<Question> getAll() {
        List<Question> questions = questionDAO.findAll();
        Collections.sort(questions, (Question q1, Question q2) -> q1.getFromPage() - q2.getFromPage());
        return questions;
    }

    @RequestMapping(value = "/pushQuestions", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<String> pushQuestions(@RequestBody List<Question> questions) {

        try {
            questionDAO.save(questions);

            for (Question q : questions) {
                for (Answer a : q.getPossibleAnswers()) {
                    a.setQuestion(q);

                    String sql = "select * from answers where question_id = :q_id and answer_id = :a_id";
                    SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
                    query.addEntity(Answer.class);
                    query.setParameter("q_id", q.getId());
                    query.setParameter("a_id", a.getAnswerId());
                    List<Answer> list = query.list();

                    if (!list.isEmpty())  a.setId(list.get(0).getId());

                    answerDAO.save(a);
                }
            }
        }
        catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/answerQuestion", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public ResponseEntity<?> answerQuestion(@RequestBody AnswerQuestionWrapper wrapper) {
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
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

}
