package ee.alekal.storage.model.dto;


import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String uuid;
    private final Integer code;
    private final String message;

    private ErrorResponse(String uuid,
                          String message,
                          Integer code) {
        this.uuid = uuid;
        this.message = message;
        this.code = code;
    }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    public static class ErrorResponseBuilder {
        private String uuid;
        private String message;
        private Integer code;

        public ErrorResponseBuilder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public ErrorResponseBuilder code(Integer code) {
            this.code = code;
            return this;
        }

        public ErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(uuid, message, code);
        }
    }
}
