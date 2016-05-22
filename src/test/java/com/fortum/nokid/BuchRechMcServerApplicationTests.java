package com.fortum.nokid;

import com.fortum.nokid.entities.VorlesungPDFDAO;
import com.fortum.nokid.entities.VorlesungPdf;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuchRechMcServerApplication.class)
@WebAppConfiguration
public class BuchRechMcServerApplicationTests {

    @Autowired
    VorlesungPDFDAO vorlesungPDFDAO;

	@Test
    public void insertPdf() {

        String pathString = "Resources/slides/02_TUM WS 2016_17_GL_final_V1_Lsg.pdf";

        try {

            Path path = Paths.get(pathString);

            byte[] arr = Files.readAllBytes(path);

            vorlesungPDFDAO.save(new VorlesungPdf("test", arr));

        } catch (Exception e) { e.printStackTrace(); }
    }

}
