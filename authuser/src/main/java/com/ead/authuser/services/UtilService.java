package com.ead.authuser.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilService {

    private String REQUEST_URI= "http://localhost:8082";

    public String createUrl(UUID userId, Pageable pageable){
        return REQUEST_URI + "/courses?userId="+ userId+ "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize()
                +"&sort=" + pageable.getSort().toString().replaceAll(": ",",");
    }

    public String createUrl(UUID courseId) {
        return REQUEST_URI + "/courses/"+ courseId;
    }
}
