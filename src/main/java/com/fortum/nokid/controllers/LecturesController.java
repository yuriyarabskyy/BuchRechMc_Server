package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.Lecture;
import com.fortum.nokid.entities.LectureDAO;
import com.fortum.nokid.entities.Question;
import com.fortum.nokid.entities.QuestionDAO;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by yuriy on 10/08/16.
 */

@Controller
@RequestMapping(value = "/api/lectures")
public class LecturesController {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private LectureDAO lectureDAO;

    private final ResourceLoader resourceLoader;

    public static final String ROOT = "upload-dir";

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public LecturesController(ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getQuestions", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Transactional
    public List<Question> getQuestions(@RequestParam("page")int page,
                                       @RequestParam(value = "chapter", required = false)Integer chapter) {

        List<Question> list = questionDAO.findByFromPageLessThanEqualPageAndToPageGreaterThanEqualPage(page);
        if (chapter != null) {
            list = list.stream()
                    .filter(question -> question.getChapter() == chapter)
                    .collect(Collectors.toList());
        }

        return list;

    }


    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public ResponseEntity<?> handleFileUpload(HttpServletRequest request){
        try {

            MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;

            MultipartFile file = mrequest.getFile("file");

            Lecture lecture = new Lecture();

            if (!file.isEmpty()) {
                try {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
                    lecture.setName(file.getOriginalFilename());
                } catch (IOException | RuntimeException e) {
                    ResponseEntity.badRequest();
                }
            } else {
                ResponseEntity.badRequest();
            }

            lecture.setStartChapter(Integer.parseInt(mrequest.getParameter("startChapter")));
            lecture.setEndChapter(Integer.parseInt(mrequest.getParameter("endChapter")));

            lectureDAO.save(lecture);
        } catch (Exception e) { ResponseEntity.badRequest(); }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {

        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(value="/getLectureByName", method = RequestMethod.GET)
    public @ResponseBody
    Lecture getLectureByName(@RequestParam("name") String fileName) {

        Lecture lecture = null;

        try {
            lecture = lectureDAO.findBynameIgnoreCase(fileName);
        } catch (Exception e) { e.printStackTrace(); }

        return lecture;

    }


    @RequestMapping(value="/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Iterable<Lecture> getAll() {
        return lectureDAO.findAll();
    }

}
