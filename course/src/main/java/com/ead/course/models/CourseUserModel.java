package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_courses_users")
public class CourseUserModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private CourseModel course;

    @Column(nullable = false)
    private UUID userId;


    public CourseUserModel(UUID id, CourseModel course, UUID userId) {
        this.id = id;
        this.course = course;
        this.userId = userId;
    }
}
