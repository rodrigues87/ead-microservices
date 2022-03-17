package com.ead.course.services.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    public List<ModuleModel> findAllByCourseCourseId(UUID uuid){
        return moduleRepository.findAllByCourseId(uuid);
    }

    @Override
    public List<ModuleModel> findModuleModelByCourseCourseId(UUID uui) {
        return moduleRepository.findAllByCourseId(uui);
    }

    @Override
    @Transactional
    public void delete(ModuleModel moduleModel) {
        Set<LessonModel> lessonModelList = moduleModel.getLessons();

        if (!lessonModelList.isEmpty()) {
            lessonRepository.deleteAll(lessonModelList);
        }
        moduleRepository.delete(moduleModel);
    }

    @Override
    public Object save(ModuleModel moduleModel) {
        return moduleRepository.save(moduleModel);
    }

    @Override
    public Optional<ModuleModel> findById(UUID courseId) {
        return moduleRepository.findById(courseId);
    }

    @Override
    public List<ModuleModel> findAll() {
        return moduleRepository.findAll();
    }


}
