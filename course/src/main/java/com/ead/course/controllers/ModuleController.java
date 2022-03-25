package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.ferramentas.Constantes;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    CourseService courseService;

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> getAllModulesFromCourse(@PathVariable UUID courseId){
        courseService.findById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllModulesFromCourse(courseId));
    }

    @PostMapping("/courses/{courseId}/modules")
    @SneakyThrows
    public ResponseEntity<Object> saveModule(@RequestBody @Valid ModuleDto moduleDto, @PathVariable UUID courseId){

        var moduleModel = new ModuleModel();
        moduleModel.aplicarDto(moduleDto);

        CourseModel courseModel = courseService.findById(courseId);

        moduleModel.setCourse(courseModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel));
    }


    @GetMapping("/modules")
    public ResponseEntity<List<ModuleModel>> getAllModules(){

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAll());
    }

    @GetMapping("/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "moduleId")UUID moduleId) throws ObjectNotFoundException {

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findById(moduleId));
    }

    @DeleteMapping("/modules/{moduleId}")
    @SneakyThrows
    public ResponseEntity<Object> deleteModule(@PathVariable(value = "moduleId")UUID moduleId){

        moduleService.delete(moduleId);

        return ResponseEntity.status(HttpStatus.CREATED).body(Constantes.MODULO_DELETADO);
    }

    @PutMapping("/modules/{moduleId}")
    @SneakyThrows
    public ResponseEntity<Object> updateModule(@PathVariable(value = "moduleId")UUID moduleId, @RequestBody @Valid ModuleDto moduleDto){

        ModuleModel moduleModel = moduleService.findById(moduleId);
        moduleModel.aplicarDto(moduleDto);

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.update(moduleModel));
    }


}
