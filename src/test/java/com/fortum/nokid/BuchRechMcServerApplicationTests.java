package com.fortum.nokid;

import com.fortum.nokid.entities.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuchRechMcServerApplication.class)
@WebAppConfiguration
public class BuchRechMcServerApplicationTests {


    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    AnswerDAO answerDAO;

    @Test
    public void questionTest() {

        try {

            Question q = questionDAO.findById(1);

            for (Answer a : q.getPossibleAnswers()) {
                System.out.println(a.getAnswer());
            }

        } catch (Exception e) { e.printStackTrace(); }

    }

    @Test
    public void userCreateTest() {

        try {

            userDAO.save(new User("Martin", "George"));

        } catch (Exception e) { e.printStackTrace(); }

    }

    @Test
    public void uqTest() {

        try {

            Question q = questionDAO.findById(2);

            int id = answerDAO.findByQuestionAndAnswerId(q, 1).getId();

            System.out.println(id);

        } catch (Exception e) { e.printStackTrace(); }

    }

}
