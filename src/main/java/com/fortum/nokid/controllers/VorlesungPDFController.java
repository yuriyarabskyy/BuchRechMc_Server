package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.VorlesungPDFDAO;
import com.fortum.nokid.entities.VorlesungPdf;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yuriy on 07.04.16.
 */

@RestController
@RequestMapping("/vorlesungen")
public class VorlesungPDFController {

    @Autowired
    private VorlesungPDFDAO vorlesungPDFDAO;


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


    @RequestMapping(value="gedPdfByName", method = RequestMethod.GET)
    public @ResponseBody VorlesungPdf getPdf(@PathVariable("fileName") String fileName) {

        VorlesungPdf vorlesung = null;

        try {

            vorlesung = vorlesungPDFDAO.getPdfByName(fileName);

            return vorlesung;


        } catch (Exception e) { e.printStackTrace(); }

        return vorlesung;

    }




}
