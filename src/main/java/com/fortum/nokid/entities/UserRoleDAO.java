package com.fortum.nokid.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yuriy on 09/10/16.
 */

@Transactional
public interface UserRoleDAO extends CrudRepository<UserRole, Long> {
}
