package com.fortum.nokid.entities;

import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

import java.util.List;

@Transactional
public interface QuestionDAO extends CrudRepository<Question, Long> {

    List<Question> findAll();

    List<Question> findByIdAndContent(long id,String content);

    Question findById(long id);

    List<Question> findByContentIgnoreCase(String content);

    List<Question> findByFromPageLessThanEqualPageAndToPageGreaterThanEqualPage(int page);

}
