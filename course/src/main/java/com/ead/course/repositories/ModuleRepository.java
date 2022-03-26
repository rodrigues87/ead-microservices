package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    @EntityGraph(attributePaths = {"course"})
    ModuleModel findByTitle(String title);

    List<ModuleModel> findAllByCourseId (UUID courseId);

    List<ModuleModel> findModuleModelByCourseId(UUID uui);

    List<ModuleModel> findModuleModelsByCourseId(UUID courseId);

}
