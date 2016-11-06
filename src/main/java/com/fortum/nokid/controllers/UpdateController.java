package com.fortum.nokid.controllers;

import com.fortum.nokid.Utils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by george on 08.05.16.
 */

@RestController
public class UpdateController {

    @RequestMapping(value="/api/updatedb",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String isUpToDate(@RequestParam("version")String version) {

        return "TODO";

    }

    @RequestMapping(value="/api/pingSlack",method = RequestMethod.GET)
    @ResponseBody
    public void sendTestSlackMessage() {
        Utils.sendErrorTextToSlack("Test message from server :)");
    }

}
