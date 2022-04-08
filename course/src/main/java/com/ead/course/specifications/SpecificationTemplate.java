package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "name", spec = LikeIgnoreCase.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "courseLevel", spec = Equal.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {}

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpec extends Specification<LessonModel> {}

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId){
        return ((root, query, criteriaBuilder) -> {
           query.distinct(true);
            Root<ModuleModel>  module = root;
            Root<CourseModel> course = query.from(CourseModel.class);
            Expression<Collection<ModuleModel>> coursesModules = course.get("modules");
            return criteriaBuilder.and(criteriaBuilder.equal(course.get("id"), courseId), criteriaBuilder.isMember(module,coursesModules));
        });
    }

    public static Specification<LessonModel> lessonModuleId(final UUID moduleId){
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<LessonModel>  lesson = root;
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> lessonsModules = module.get("lessons");
            return criteriaBuilder.and(criteriaBuilder.equal(module.get("id"), moduleId), criteriaBuilder.isMember(lesson,lessonsModules));
        });
    }

    public static Specification<CourseUserModel> courseUserId(final UUID userId){
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            Join<CourseModel, CourseUserModel> courseProd = root.join("coursesUsers");
            return criteriaBuilder.equal(courseProd.get("userId"),userId);
        });
    }
}
