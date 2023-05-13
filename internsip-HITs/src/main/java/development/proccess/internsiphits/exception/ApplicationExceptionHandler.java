package development.proccess.internsiphits.exception;

import development.proccess.internsiphits.exception.characteristics.CharacteristicsException;
import development.proccess.internsiphits.exception.user.UnauthorizedException;
import development.proccess.internsiphits.exception.user.UserAlreadyExistsException;
import development.proccess.internsiphits.exception.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ApplicationException> handleINotValidArgument(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        List<ApplicationException> exceptions = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(
                ex -> exceptions.add(
                        new ApplicationException(
                                ex.getField() + " - " + ex.getDefaultMessage(),
                                HttpStatus.BAD_REQUEST,
                                LocalDateTime.now()
                        )
                )
        );
        return exceptions;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationException handleUserNotFound(UserNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ApplicationException(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationException handleUserAlreadyExists(UserAlreadyExistsException exception) {
        log.error(exception.getMessage(), exception);
        return new ApplicationException(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApplicationException handleUnauthorizedException(UnauthorizedException exception) {
        log.error(exception.getMessage(), exception);
        return new ApplicationException(exception.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now());
    }

    @ExceptionHandler(CharacteristicsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationException handleCharacteristicsException(CharacteristicsException exception) {
        log.error(exception.getMessage(), exception);
        return new ApplicationException(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }
}