package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonModel, UUID> {

    List<LessonModel> findLessonModelsById(UUID uuid);

    Set<LessonModel> findLessonModelsByModuleId(UUID modelId);

}
