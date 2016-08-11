package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.Lecture;
import com.fortum.nokid.entities.LectureDAO;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by yuriy on 10/08/16.
 */

@Controller
@RequestMapping(value = "/lectures")
public class LecturesController {

    @Autowired
    private LectureDAO lectureDAO;

    private final ResourceLoader resourceLoader;

    public static final String ROOT = "upload-dir";

    @Autowired
    public LecturesController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String provideUploadInfo(Model model) throws IOException {

        model.addAttribute("files", Files.walk(Paths.get(ROOT))
                .filter(path -> !path.equals(Paths.get(ROOT)))
                .map(path -> Paths.get(ROOT).relativize(path))
                .map(path -> linkTo(methodOn(LecturesController.class).getFile(path.toString())).withRel(path.toString()))
                .collect(Collectors.toList()));

        return "uploadForm";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String handleFileUpload(//@RequestParam("file") MultipartFile file,
                                   //@RequestParam("startChapter") String startChapter,
                                   //@RequestParam("endChapter") String endChapter,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {
        try {

            MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;

            MultipartFile file = mrequest.getFile("file");

            Lecture lecture = new Lecture();

            if (!file.isEmpty()) {
                try {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
                    redirectAttributes.addFlashAttribute("message",
                            "You successfully uploaded " + file.getOriginalFilename() + "!");
                    lecture.setName(file.getOriginalFilename());
                } catch (IOException | RuntimeException e) {
                    redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
                }
            } else {
                redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
            }

            lecture.setStartChapter(Integer.parseInt(mrequest.getParameter("startChapter")));
            lecture.setEndChapter(Integer.parseInt(mrequest.getParameter("endChapter")));

            lectureDAO.save(lecture);
        } catch (Exception e) { e.printStackTrace(); }

        return "redirect:/lectures/";
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

            return lecture;


        } catch (Exception e) { e.printStackTrace(); }

        return lecture;

    }

}
