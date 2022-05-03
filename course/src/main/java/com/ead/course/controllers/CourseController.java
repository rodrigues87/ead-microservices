package com.ead.course.controllers;


import com.ead.course.dtos.*;
import com.ead.course.models.*;
import com.ead.course.services.*;
import com.ead.course.specifications.*;
import lombok.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseDto courseDto) {

        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto, courseModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseModel));
    }

    @DeleteMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {

        courseService.delete(courseId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Curso deletado com sucesso");
    }


    @PutMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid CourseDto courseDto) {

        CourseModel courseModel = courseService.findById(courseId);
        BeanUtils.copyProperties(courseDto, courseModel);

        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(courseModel));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses
            (SpecificationTemplate.CourseSpec spec,
             @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
             @RequestParam(required = false) UUID userId) {


        Page<CourseModel> courseModelPage = null;

        if (userId != null) {
            courseModelPage = courseService.findAllCoursesFromUser(SpecificationTemplate.courseUserId(userId).and(spec), pageable);

        }else {
            courseModelPage = courseService.findAll(spec,pageable);

        }

        return ResponseEntity.status(HttpStatus.OK).body(courseModelPage);
    }


    @GetMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {

        CourseModel courseModel = courseService.findById(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(courseModel);
    }
}
