package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.ModuleService;
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
@RequestMapping("/modules")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @PostMapping
    public ResponseEntity<Object> saveModule(@RequestBody @Valid ModuleDto moduleDto){
        var moduleModel = new ModuleModel();

        BeanUtils.copyProperties(moduleDto,moduleModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        moduleModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel));
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable(value = "moduleId")UUID moduleId){

        Optional<ModuleModel> moduleModel = moduleService.findById(moduleId);

        if(moduleModel.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }

        moduleService.delete(moduleModel.get());

        return ResponseEntity.status(HttpStatus.CREATED).body("Curso deletado com sucesso");
    }


    @PutMapping("/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "moduleId")UUID moduleId, @RequestBody @Valid ModuleDto moduleDto){

        Optional<ModuleModel> moduleModel = moduleService.findById(moduleId);

        if(moduleModel.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }

        BeanUtils.copyProperties(moduleDto, moduleModel);

        moduleModel.get().setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

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

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(moduleModel);
    }
}
