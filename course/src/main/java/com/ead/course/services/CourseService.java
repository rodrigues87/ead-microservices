package com.ead.course.services;

import com.ead.course.ferramentas.Constantes;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.specifications.SpecificationTemplate;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService extends AbstractService<CourseModel,SpecificationTemplate.CourseSpec> {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;

    @Override
    public Page<CourseModel> findAll(SpecificationTemplate.CourseSpec spec, Pageable pageable) {
        return courseRepository.findAll(spec,pageable);
    }

    @Override
    public CourseModel save(CourseModel obj) {
        return courseRepository.save(obj);
    }

    @Override
    public CourseModel update(CourseModel obj) throws ObjectNotFoundException {
        verifyIfExists(obj);

        return courseRepository.save(obj);
    }

    @Override
    public void delete(UUID id) throws ObjectNotFoundException {
        CourseModel courseModel = findById(id);

        Set<ModuleModel> models = courseModel.getModules();
        if(!models.isEmpty()){
            for (ModuleModel moduleModel : models){
                List<LessonModel> lessonModels = lessonRepository.findLessonModelsById(moduleModel.getId());

                if(!lessonModels.isEmpty()){
                    lessonRepository.deleteAll(lessonModels);
                }
            }
            moduleService.deleteAll(models);
        }
        courseRepository.delete(courseModel);
    }

    @SneakyThrows
    @Override
    public CourseModel findById(UUID id) {
        Optional<CourseModel> courseModel =  courseRepository.findById(id);
        if(courseModel.isEmpty()){
            throw new RuntimeException(Constantes.CURSO_NAO_ENCONTRADO);
        }
        return courseModel.get();
    }

    @Override
    public void deleteAll(Set<CourseModel> models) {
        courseRepository.deleteAll(models);
    }
}
