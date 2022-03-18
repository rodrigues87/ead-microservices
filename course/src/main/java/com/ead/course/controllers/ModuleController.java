package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.ferramentas.Constantes;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import com.ead.course.services.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses/{courseId}/modules")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> saveModule(@RequestBody @Valid ModuleDto moduleDto, @PathVariable UUID courseId){

        var moduleModel = new ModuleModel();
        moduleModel.aplicarDto(moduleDto);

        var courseModel = courseServiceImpl.findById(courseId);

        if(courseModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constantes.CURSO_NAO_ENCONTRADO);
        }

        moduleModel.setCourse(courseModel.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel));
    }
    

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable(value = "moduleId")UUID moduleId){

        Optional<ModuleModel> moduleModel = moduleService.findById(moduleId);

        if(moduleModel.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constantes.MODULO_NAO_ENCONTRADO);
        }

        moduleService.delete(moduleModel.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(Constantes.MODULO_DELETADO);
    }


    @PutMapping("/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "moduleId")UUID moduleId, @RequestBody @Valid ModuleDto moduleDto){

        Optional<ModuleModel> moduleModel = moduleService.findById(moduleId);

        if(moduleModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constantes.MODULO_NAO_ENCONTRADO);
        }

        moduleModel.get().aplicarDto(moduleDto);

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(moduleModel.get()));
    }

    @GetMapping
    public ResponseEntity<List<ModuleModel>> getAllModules(){

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAll());
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "moduleId")UUID moduleId){

        Optional<ModuleModel> moduleModel = moduleService.findById(moduleId);

        if(moduleModel.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constantes.CURSO_NAO_ENCONTRADO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(moduleModel);
    }
}
