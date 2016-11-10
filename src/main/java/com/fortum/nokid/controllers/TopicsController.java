package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.Topic;
import com.fortum.nokid.entities.TopicDAO;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yuriy on 13/08/16.
 */

@RestController
@RequestMapping("/api/topics")
public class TopicsController {

    @Autowired
    private TopicDAO topicDAO;

    @Autowired
    private SessionFactory sessionFactory;

//    @CrossOrigin(origins = "*")
    @Transactional
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Object> getAllTopics() {
        String s = "select topic from topics " +
                "order by chapter ASC";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(s);
        List<Object> list = query.list();
        return list;
    }

//    @CrossOrigin(origins = "*")
    @ResponseBody
    @RequestMapping(value = "/addTopic", method = RequestMethod.POST)
    public ResponseEntity<?> addTopic(@RequestBody Topic topic) {
        try {
            topicDAO.save(topic);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

}
