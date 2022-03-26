package com.ead.course.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDto implements Serializable {
    @NonNull
    private final String title;
    @NonNull
    private final String description;
    @NonNull
    private final String videoUrl;

    private LocalDateTime creationDate;
}
