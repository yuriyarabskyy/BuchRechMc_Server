package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.User;
import com.fortum.nokid.entities.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yuriy on 06.04.16.
 */

@RequestMapping("/api/users")
@RestController
public class UsersController {

    @Autowired
    private UserDAO userDAO;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody User user) {
        try {
            userDAO.save(user);
        } catch (Exception e) {
            return "Error creating the user";
        }

        return "Successfully create user with the id = " + user.getId();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getUsersByName", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Iterable<User> getUsersByLastName(@RequestParam(value = "", required = false) String lastName,
                                             @RequestParam(value = "", required = false) String firstName) {
        List<User> users = new ArrayList<>();
        if (!lastName.isEmpty()) users = userDAO.findBylastNameIgnoreCase(lastName);
        if (!firstName.isEmpty() && !lastName.isEmpty()) users.stream()
                .filter(user -> user.getFirstName().equals(firstName))
                .collect(Collectors.toList());
        if (lastName.isEmpty() && !firstName.isEmpty())
            users = userDAO.findByfirstNameIgnoreCase(firstName);
        return users;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Iterable<User> getAll() { return userDAO.findAll(); }

}
