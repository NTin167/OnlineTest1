//package com.ptithcm.onlinetest.model;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Table(name = "CourseType")
//
//public class CourseType {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "name")
//    private String name;
//    @Column(name = "describer")
//    private String describer;
////    @ToString.Exclude
//    @OneToMany(mappedBy = "courseType")
//    @JsonIgnore
//    private Set<Course> courses;
//}
