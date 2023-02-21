package com.ptithcm.onlinetest.model;

import com.ptithcm.onlinetest.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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


}
