package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.KontoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuriy on 07.04.16.
 */

@RestController
@RequestMapping("/konto")
public class KontoController {

    @Autowired
    private KontoDAO kontoDAO;

}
