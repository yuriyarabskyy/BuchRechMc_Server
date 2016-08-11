package com.fortum.nokid.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yuriy on 06.04.16.
 */

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

    User findById(long id);

    List<User> findBylastNameIgnoreCase(String lastName);

}
