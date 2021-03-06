package com.ead.course.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GenericError extends ErrorDetail {
    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

        private Builder() {

        }

        public static Builder newBuilder() {
            return new Builder();
        }


        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }


        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public GenericError build() {

            GenericError objetctNotFound = new GenericError();

            objetctNotFound.setDeveloperMessage(developerMessage);
            objetctNotFound.setTitle(title);
            objetctNotFound.setDetail(detail);
            objetctNotFound.setTimestamp(timestamp);
            objetctNotFound.setStatus(status);


            return objetctNotFound;
        }
    }
}
