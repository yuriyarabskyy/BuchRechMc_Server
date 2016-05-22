package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.VorlesungPDFDAO;
import com.fortum.nokid.entities.VorlesungPdf;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yuriy on 07.04.16.
 */

@RestController
@RequestMapping("/vorlesungen")
public class VorlesungPDFController {

    @Autowired
    private VorlesungPDFDAO vorlesungPDFDAO;


    @RequestMapping(value="insertPdf", method = RequestMethod.POST)
    public @ResponseBody String insertPdf(@RequestParam("fileName") String fileName, @RequestParam("content") String content) {

        // to receive this string, I have to encode it the same way into a base64string
        byte[] byteContent = Base64.decodeBase64(content);

        try {

            vorlesungPDFDAO.save(new VorlesungPdf(fileName, byteContent));

        } catch (Exception e) {

            return "Something went horribly wrong";

        }

        return "Your pdf was successfully stored in the database";

    }


    @RequestMapping(value="gedPdfByName", method = RequestMethod.GET)
    public @ResponseBody VorlesungPdf getPdf(@PathVariable("fileName") String fileName) {

        VorlesungPdf vorlesung = null;

        try {

            vorlesung = vorlesungPDFDAO.findByNameIgnoreCase(fileName);

            return vorlesung;


        } catch (Exception e) { e.printStackTrace(); }

        return vorlesung;

    }




}
