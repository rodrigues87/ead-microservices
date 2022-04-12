package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.services.UtilService;
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
public class UserClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${ead.api.url.course}")
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

    public CourseDto findCourseById(UUID courseId){
        CourseDto courseDto = null;
        ResponseEntity<CourseDto> result = null;

        String url = new UtilService().createUrl(courseId);

        try {
            ParameterizedTypeReference<CourseDto> responseType =
                    new ParameterizedTypeReference<CourseDto>() {};

            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            courseDto = result.getBody();

        }catch (Exception e){
            log.error("Error request /courses {}", e);
        }
        return courseDto;
    }

}
