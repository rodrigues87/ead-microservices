package com.ead.course.services;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
    List<ModuleModel> findAllByCourseCourseId(UUID uui);

    List<ModuleModel> findModuleModelByCourseCourseId(UUID uui);

    void delete(ModuleModel moduleModel);

    Object save(ModuleModel moduleModel);

    Optional<ModuleModel> findById(UUID courseId);

    List<ModuleModel> findAll();
}
