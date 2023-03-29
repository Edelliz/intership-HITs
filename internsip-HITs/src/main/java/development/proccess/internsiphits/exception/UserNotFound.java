package development.proccess.internsiphits.exception;

public class UserNotFound extends RuntimeException{
    UserNotFound(String msg) {
        super(msg);
    }
}
