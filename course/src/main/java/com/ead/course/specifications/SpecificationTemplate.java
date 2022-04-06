package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And({
            @Spec(path ="name", spec = LikeIgnoreCase.class),
            @Spec(path ="courseStatus", spec = Equal.class),
            @Spec(path ="courseLevel", spec = Equal.class)
    })
    public interface CourseSpec extends Specification<CourseModel>{

    }

    @And({
            @Spec(path ="title", spec = Like.class),
    })
    public interface ModuleSpec extends Specification<ModuleModel>{

    }

    @And({
            @Spec(path ="userType", spec = Equal.class),
            @Spec(path ="email", spec = Like.class),
            @Spec(path ="userStatus", spec = Equal.class)
    })

    public interface LessonSpec extends Specification<LessonModel>{

    }
}