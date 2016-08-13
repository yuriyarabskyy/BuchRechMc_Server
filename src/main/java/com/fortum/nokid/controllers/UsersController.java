package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.User;
import com.fortum.nokid.entities.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by yuriy on 06.04.16.
 */

@RequestMapping("/api/users")
@RestController
public class UsersController {

    private static final String TUM_EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "(my)?tum(\\.de)$";
    private Pattern emailPattern = Pattern.compile(TUM_EMAIL_PATTERN);
    private Matcher matcher;

    @Autowired
    private UserDAO userDAO;


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String create(@RequestBody User user) {
        try {

            matcher = emailPattern.matcher(user.getEmail());

            if (!matcher.matches()) {

                String token = UUID.randomUUID().toString();

                String to = user.getEmail();

                String from = "buchrechmc@h2578248.stratoserver.net";

                String host = "localhost";

                Properties properties = System.getProperties();

                properties.setProperty("mail.smtp.host", host);

                Session session = Session.getDefaultInstance(properties);

                MimeMessage message = new MimeMessage(session);

                message.setFrom(new InternetAddress(from));

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                message.setSubject("Registration - Buchrechnung");

                message.setText("Register your user by clicking on this link:" +
                        " http://localhost:8080/api/users/verify?token=" + token);

                Transport.send(message);

                user.setToken(token);

                userDAO.save(user);

                return "Success creating user with id: " + user.getId();
            }

        } catch (Exception e) {
            return "Error creating the user";
        }

        return "Email didn't match";
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
