package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.User;
import com.fortum.nokid.entities.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuriy on 06.04.16.
 */

@RequestMapping("/users")
@RestController
public class UsersController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/getDummyUsers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getAllUsers() {

        List<User> users = new LinkedList<>();

        users.add(new User("Sam", 28, null));
        users.add(new User("Dean", 45, null));

        return users;

    }

    @RequestMapping(value = "/create/{name}", method = RequestMethod.GET)
    public String create(@PathVariable("name")String name) {
        User user;
        try {
            user = new User(name, 0, null);
            userDAO.save(user);
        } catch (Exception e) { return "Error creating the user"; }

        return "Successfully create user with the id = " + user.getId();
    }

    @RequestMapping(value = "/getUsersByName", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Iterable<User> getUsersByName(@RequestParam("name")String name) {
        return userDAO.findByNameIgnoreCase(name);
    }




}
