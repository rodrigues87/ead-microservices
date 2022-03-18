package com.ead.course.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetail {

    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String developerMessage;
}
