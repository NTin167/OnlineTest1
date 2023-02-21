package com.ptithcm.onlinetest.model;

import com.ptithcm.onlinetest.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Instant publishedAt;

    private Instant startsAt;

    private Instant endsAt;

    private String content;

    //host id is foreign key of User
//    @ManyToOne(fetch = FetchType.EAGER)
//    Category category;
//
//    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<Question> questions = new HashSet<>();

}
