package com.ptithcm.onlinetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptithcm.onlinetest.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "quiz")
public class Quiz extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String metaTitle;

    private String slug;

    private String summary;

    private int type;

    private int score;

    private int published;

    private Instant publishedAt;

    private Instant startsAt;

    private Instant endsAt;

    private String content;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<QuizQuestion> quizQuestions = new HashSet<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<QuizAnswer> quizzAnswers = new HashSet<>();

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Take> takes = new HashSet<>();

    //host id is foreign key of User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
