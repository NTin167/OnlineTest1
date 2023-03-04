package com.ptithcm.onlinetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptithcm.onlinetest.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "take")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Take extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int status;

    private int score;

    private int published;

    private Instant startedAt;

    private Instant finishedAt;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;

    @OneToMany(mappedBy = "take")
    @JsonIgnore
    private Set<TakeAnswer> takeAnswers = new HashSet<>();
}
