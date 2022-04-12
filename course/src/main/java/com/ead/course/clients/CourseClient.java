package com.ead.course.clients;

import com.ead.course.dtos.CourseDto;
import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.services.UtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${ead.api.url.authuser}")
    private String REQUEST_URI;

    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable){

        List<CourseDto> searchResult = null;
        ResponseEntity<ResponsePageDto<CourseDto>> result = null;

        String url = new UtilService().createUrl(userId,pageable);

        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType =
                    new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};

            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

        }catch (Exception e){
            log.error("Error request /courses {}", e);
        }

        return result.getBody();
    }

}
