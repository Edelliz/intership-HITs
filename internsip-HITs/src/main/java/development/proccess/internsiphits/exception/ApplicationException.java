package development.proccess.internsiphits.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApplicationException {

    private String message;

    private HttpStatus status;

    private LocalDateTime timeStamp;

    public ApplicationException(String message, HttpStatus status, LocalDateTime timeStamp) {
        this.message = message;
        this.status = status;
        this.timeStamp = timeStamp;
    }
}