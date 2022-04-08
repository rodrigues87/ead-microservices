package com.ead.course.controllers;


import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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
             @RequestParam(required = false)UUID userId) {


        Page<CourseModel> courseModelPage = null;

        if(userId != null){
            courseModelPage = courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable);

        }

        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {

        CourseModel courseModel = courseService.findById(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(courseModel);
    }
}
