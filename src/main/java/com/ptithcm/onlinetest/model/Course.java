//package com.ptithcm.onlinetest.model;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//@Table(name = "Course")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Course {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "name")
//    private String name;
//    @Column(name = "duration")
//    private Long duration;
//    @Column(name = "status")
//    private int status;
//    @Column(name = "price")
//    private float price;
////
////    @OneToMany(mappedBy = "course")
////    @JsonIgnore
////    private Set<Class> classes;
//    @ToString.Exclude
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(nullable = true)
////    @JsonIgnore
//    private CourseType courseType;
//}
