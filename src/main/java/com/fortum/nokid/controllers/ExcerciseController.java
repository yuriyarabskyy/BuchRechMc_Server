package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.ExcerciseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuriy on 07.04.16.
 */

@RestController
@RequestMapping("/excercise")
public class ExcerciseController {

    @Autowired
    private ExcerciseDAO excerciseDAO;

}
