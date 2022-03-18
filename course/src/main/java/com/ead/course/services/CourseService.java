package com.ead.course.services;

import com.ead.course.ferramentas.Constantes;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService extends AbstractService<CourseModel> {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;

    @Override
    public List<CourseModel> findAll() {
        return courseRepository.findAll();
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

    @Override
    public CourseModel findById(UUID id) throws ObjectNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Constantes.CURSO_NAO_ENCONTRADO));
    }

    @Override
    public void deleteAll(Set<CourseModel> models) {
        courseRepository.deleteAll(models);
    }
}
