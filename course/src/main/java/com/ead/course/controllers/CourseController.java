package com.ead.course.controllers;


import com.ead.course.dtos.CourseDto;
import com.ead.course.ferramentas.Constantes;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseDto courseDto){
        var courseModel = new CourseModel();

        BeanUtils.copyProperties(courseDto,courseModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseModel));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId")UUID courseId){

        Optional<CourseModel> courseModel = courseService.findById(courseId);

        if(courseModel.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constantes.CURSO_NAO_ENCONTRADO);
        }

        courseService.delete(courseModel.get());

        return ResponseEntity.status(HttpStatus.CREATED).body("Curso deletado com sucesso");
    }


    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId")UUID courseId, @RequestBody @Valid CourseDto courseDto){

        Optional<CourseModel> courseModel = courseService.findById(courseId);

        if(courseModel.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }

        BeanUtils.copyProperties(courseDto, courseModel);

        courseModel.get().setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(courseModel.get()));
    }

    @GetMapping
    public ResponseEntity<List<CourseModel>> getAllCourses(){

        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId")UUID courseId){

        Optional<CourseModel> courseModel = courseService.findById(courseId);

        if(courseModel.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(courseModel);
    }
}
