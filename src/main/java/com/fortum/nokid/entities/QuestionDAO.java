package com.fortum.nokid.entities;

import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

import java.util.List;

@Transactional
public interface QuestionDAO extends CrudRepository<Question, Long> {

    List<Question> findByIdAndContent(long id,String content);

    Question findById(long id);

    Question findByContentIgnoreCase(String content);

}
