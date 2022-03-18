package com.ead.course.error;

public class ValidationErrorDetails extends ErrorDetail {

    private String field;
    private String fieldMesage;

    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;
        private String field;
        private String fieldMessage;

        private Builder() {

        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public void ValidationErrorDetails() {

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

        public Builder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public ValidationErrorDetails build() {

            ValidationErrorDetails validatorErrorDetail = new ValidationErrorDetails();
            validatorErrorDetail.setDeveloperMessage(developerMessage);
            validatorErrorDetail.setTitle(title);
            validatorErrorDetail.setDetail(detail);
            validatorErrorDetail.setTimestamp(timestamp);
            validatorErrorDetail.setStatus(status);
            validatorErrorDetail.field = field;
            validatorErrorDetail.fieldMesage = fieldMessage;
            return validatorErrorDetail;
        }

    }
}
