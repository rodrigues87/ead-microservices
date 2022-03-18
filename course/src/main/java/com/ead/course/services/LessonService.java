package com.ead.course.services;

import com.ead.course.models.LessonModel;
import com.ead.course.repositories.LessonRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class LessonService extends AbstractService<LessonModel> {
    @Autowired
    LessonRepository lessonRepository;

    @Override
    public List<LessonModel> findAll() {
        return null;
    }

    @Override
    public LessonModel save(LessonModel obj) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public LessonModel update(LessonModel obj) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void delete(UUID id) throws ObjectNotFoundException {

    }

    @Override
    public LessonModel findById(UUID id) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void deleteAll(Set<LessonModel> models) {

    }
}
