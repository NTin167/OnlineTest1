package com.ptithcm.onlinetest.repository;

import com.ptithcm.onlinetest.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, JpaSpecificationExecutor<Quiz> {
    boolean existsByTitle(String title);
    List <Quiz> findAllByCategory_Title(String titleCategory);

//    List<Quiz> findAllByCategory_Title(String titleCategopry);

}
