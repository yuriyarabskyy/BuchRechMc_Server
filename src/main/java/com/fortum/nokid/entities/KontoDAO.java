package com.fortum.nokid.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yuriy on 07.04.16.
 */

@Transactional
public interface KontoDAO extends CrudRepository<Konto, Long> {
}
