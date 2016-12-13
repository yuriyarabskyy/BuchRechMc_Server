package com.fortum.nokid.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yuriy on 06.04.16.
 */

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

    User findById(int id);

    List<User> findBytoken(String token);

    List<User> findBylastNameIgnoreCase(String lastName);

    List<User> findByfirstNameIgnoreCase(String lastName);

}
