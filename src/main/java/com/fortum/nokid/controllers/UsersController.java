package com.fortum.nokid.controllers;

import com.fortum.nokid.entities.User;
import com.fortum.nokid.entities.UserDAO;
import com.fortum.nokid.entities.UserRole;
import com.fortum.nokid.entities.UserRoleDAO;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    private static final String UNVERIFIED = "unverified";
    private static final String USER = "ROLE_USER";

    @Autowired
    private HibernateTransactionManager tr;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private SessionFactory sessionFactory;


    @CrossOrigin(origins = "*")
    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody User user) {
        try {

            matcher = emailPattern.matcher(user.getEmail());

            if (matcher.matches()) {

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
                        " http://85.214.195.89:8080/api/users/verify?token=" + token);

                Transport.send(message);

                user.setToken(token);
                user.setEnabled(true);

                userDAO.save(user);

                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public ResponseEntity<?> verify(@RequestParam("token") String token) {

        org.hibernate.Session session = tr.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "select * from users where token = :token";

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(User.class);
        query.setParameter("token", token);

        List<User> users = query.list();

        if (users.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        session.getTransaction().commit();
        UserRole userRole = new UserRole();

        userRole.setEmail(users.get(0).getEmail());

        userRole.setRole(USER);

        session.beginTransaction();
        userRoleDAO.save(userRole);
        session.getTransaction().commit();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
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
