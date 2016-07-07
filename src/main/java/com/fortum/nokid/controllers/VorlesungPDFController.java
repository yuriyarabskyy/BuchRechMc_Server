package com.fortum.nokid.controllers;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.fortum.nokid.entities.VorlesungPDFDAO;
import com.fortum.nokid.entities.VorlesungPdf;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Created by yuriy on 07.04.16.
 */

@Controller
@RequestMapping("/vorlesungen")
public class VorlesungPDFController {

    @Autowired
    private VorlesungPDFDAO vorlesungPDFDAO;

    private final ResourceLoader resourceLoader;

    public static final String ROOT = "upload-dir";

    @Autowired
    public VorlesungPDFController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String provideUploadInfo(Model model) throws IOException {

        model.addAttribute("files", Files.walk(Paths.get(ROOT))
                .filter(path -> !path.equals(Paths.get(ROOT)))
                .map(path -> Paths.get(ROOT).relativize(path))
                .map(path -> linkTo(methodOn(VorlesungPDFController.class).getFile(path.toString())).withRel(path.toString()))
                .collect(Collectors.toList()));

        return "uploadForm";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (!file.isEmpty()) {
            try {
                Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!");
            } catch (IOException|RuntimeException e) {
                redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
        }

        return "redirect:/vorlesungen/";
    }


    /*
    @RequestMapping(value="/doUpload", method = RequestMethod.POST)
    public String handlePdfUpload(HttpServletRequest request, @RequestParam CommonsMultipartFile[] fileUpload) throws Exception {

        if (fileUpload != null && fileUpload.length > 0) {
            for (CommonsMultipartFile file : fileUpload) {

                System.out.println("Saving file: " + file.getOriginalFilename());

                VorlesungPdf vorlesungPdf = new VorlesungPdf();
                vorlesungPdf.setName(file.getName());
                vorlesungPdf.setContent(file.getBytes());
                vorlesungPDFDAO.insertPdf(vorlesungPdf);
            }
        }

        return "Your pdf was successfully stored in the database";

    }
*/
    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {

        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

/*
    @RequestMapping(value="gedPdfByName", method = RequestMethod.GET)
    public @ResponseBody VorlesungPdf getPdf(@PathVariable("fileName") String fileName) {

        VorlesungPdf vorlesung = null;

        try {

            vorlesung = vorlesungPDFDAO.findByNameIgnoreCase(fileName);

            return vorlesung;


        } catch (Exception e) { e.printStackTrace(); }

        return vorlesung;

    }
*/



}
