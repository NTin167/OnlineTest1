package com.ptithcm.onlinetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptithcm.onlinetest.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "quiz_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestion extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private int active;

    private int level;

    private int score;

    private String content;


    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;

//    @OneToMany(mappedBy = "quizQuestion")
//    @JsonIgnore
//    private Set<QuizAnswer> quizzAnswers = new HashSet<>();
//
//    @OneToMany(mappedBy = "quizQuestion")
//    @JsonIgnore
//    private Set<TakeAnswer> takeAnswers = new HashSet<>();

}
