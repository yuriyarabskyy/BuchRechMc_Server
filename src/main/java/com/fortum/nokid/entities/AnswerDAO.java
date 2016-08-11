package com.fortum.nokid.entities;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by yuriy on 11/08/16.
 */
public interface AnswerDAO extends CrudRepository<Answer, Long> {

    Answer findByQuestionAndAnswerId(Question question, int answerId);

}
