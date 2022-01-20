package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    @EntityGraph(attributePaths = {"course"})
    ModuleModel findByTitle(String title);

    List<ModuleModel> findAllByCourseCourseId (UUID courseId);

    @Modifying
    @Query(value = "select * from module_model where course_course_id = : courseId", nativeQuery = true)
    List<ModuleModel> findModuleModelByCourseCourseId(UUID uui);
}
