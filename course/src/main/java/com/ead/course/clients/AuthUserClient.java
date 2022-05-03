package com.ead.course.clients;

import com.ead.course.dtos.*;
import com.ead.course.models.*;
import com.ead.course.services.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import javax.transaction.*;
import java.util.*;

@Log4j2
@Component
public class AuthUserClient {

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

    public ResponseEntity<UserDto> findUserById(UUID uuid){
        String url = new UtilService().createUrlFindById(uuid);

        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
    }


    @Transactional
    public void subscriptionUserInCourse(CourseUserModel courseUserModel, UUID userId) {
        String url = REQUEST_URI + "/user/"+userId+"/"+courseUserModel.getId();

        restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
    }
}
