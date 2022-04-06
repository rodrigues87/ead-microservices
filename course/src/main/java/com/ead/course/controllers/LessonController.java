package com.ead.course.controllers;

import com.ead.course.dtos.LessonDto;
import com.ead.course.ferramentas.Constantes;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplate;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("")
public class LessonController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    LessonService lessonService;

    @SneakyThrows
    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> getAllLessonsFromModule(@PathVariable UUID moduleId){
        moduleService.findById(moduleId);
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findLessonModelsByModuleId(moduleId));
    }

    @PostMapping("/modules/{moduleId}/lessons")
    @SneakyThrows
    public ResponseEntity<Object> saveLesson(@RequestBody @Valid LessonDto lessoNDto, @PathVariable UUID moduleId){

        ModuleModel moduleModel = moduleService.findById(moduleId);

        var lessonModel = new LessonModel();

        lessonModel.aplicarDto(lessoNDto);

        lessonModel.setModule(moduleModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel));
    }


    @GetMapping("/lessons")
    public ResponseEntity<Page<LessonModel>> getAllLessons(SpecificationTemplate.LessonSpec spec,
                                                           @PageableDefault(page = 0,size = 10,sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAll(spec, pageable));
    }

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(@PathVariable(value = "lessonId") UUID lessonId) throws ObjectNotFoundException {

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findById(lessonId));
    }

    @DeleteMapping("/lessons/{lessonId}")
    @SneakyThrows
    public ResponseEntity<Object> deleteLesson(@PathVariable(value = "lessonId") UUID lessonId){

        lessonService.delete(lessonId);

        return ResponseEntity.status(HttpStatus.CREATED).body(Constantes.LESSON_DELETADO);
    }

    @PutMapping("/lessons/{lessonId}")
    @SneakyThrows
    public ResponseEntity<Object> updateLesson(@PathVariable(value = "lessonId")UUID lessonId, @RequestBody @Valid LessonDto lessonDto){

        LessonModel lessonModel = lessonService.findById(lessonId);
        lessonModel.aplicarDto(lessonDto);

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.update(lessonModel));
    }


}
