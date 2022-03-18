package com.ead.course.controllers;


import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        BeanUtils.copyProperties(courseDto,courseModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseModel));
    }

    @DeleteMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId")UUID courseId)  {

        courseService.delete(courseId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Curso deletado com sucesso");
    }


    @PutMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId")UUID courseId, @RequestBody @Valid CourseDto courseDto){

        CourseModel courseModel = courseService.findById(courseId);
        BeanUtils.copyProperties(courseDto, courseModel);

        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(courseModel));
    }

    @GetMapping
    public ResponseEntity<List<CourseModel>> getAllCourses(){

        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll());
    }

    @GetMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId")UUID courseId){

        CourseModel courseModel = courseService.findById(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(courseModel);
    }
}
