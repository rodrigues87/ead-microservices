package com.ead.course.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilService {

    @Value("${ead.api.url.authuser}")
    private String REQUEST_URI;

    public String createUrl(UUID courseId, Pageable pageable){
        return REQUEST_URI + "/users?courseId="+ courseId+ "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize()
                +"&sort=" + pageable.getSort().toString().replaceAll(": ",",");
    }

    public String createUrlFindById(UUID userId){
        return REQUEST_URI + "/users?userId=" + userId;
    }
}
