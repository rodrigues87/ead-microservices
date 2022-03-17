package com.ead.course.controllers;


import com.ead.course.dtos.ModuleDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;



    @PrePersist
    public void basicPrePersist() {
        this.creationDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    public void basicPreUpdate() {
        this.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"));

    }

    public void aplicarDto(Object moduleDto){
        BeanUtils.copyProperties(moduleDto,this);
    }


}
