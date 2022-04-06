package com.ead.course.services;

import com.ead.course.ferramentas.Constantes;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.specifications.SpecificationTemplate;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModuleService extends AbstractService<ModuleModel,SpecificationTemplate.ModuleSpec>{

    private final ModuleRepository moduleRepository;
    private final LessonService lessonService;


    @Override
    public Page<ModuleModel> findAll(SpecificationTemplate.ModuleSpec moduleSpec, Pageable pageable) {
        return moduleRepository.findAll(moduleSpec,pageable);
    }

    @Override
    public ModuleModel save(ModuleModel moduleModel) throws ObjectNotFoundException  {

        if(moduleModel.getCourse() ==null){
            throw new ObjectNotFoundException(Constantes.CURSO_NAO_ENCONTRADO);
        }
        return moduleRepository.save(moduleModel);
    }

    @Override
    public ModuleModel update(ModuleModel obj) throws ObjectNotFoundException {
        return moduleRepository.save(obj);
    }

    @Override
    public void delete(UUID id) throws ObjectNotFoundException {

        findById(id);

        Set<LessonModel> lessonModels = lessonService.findLessonModelsByModuleId(id);

        if(!lessonModels.isEmpty()){
            lessonService.deleteAll(lessonModels);
        }


        moduleRepository.deleteById(id);
    }

    @Override
    public ModuleModel findById(UUID id) throws ObjectNotFoundException {
        return moduleRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Constantes.MODULO_NAO_ENCONTRADO));
    }

    @Override
    public void deleteAll(Set<ModuleModel> models) {
        moduleRepository.deleteAll(models);

    }

    public Object findAllModulesFromCourse(UUID courseId) {
        return moduleRepository.findModuleModelsByCourseId(courseId);
    }

    public Page<ModuleModel> findAllModulesFromCourse(Specification<ModuleModel> specification, Pageable pageable) {
        return moduleRepository.findAll(specification,pageable);
    }
}
