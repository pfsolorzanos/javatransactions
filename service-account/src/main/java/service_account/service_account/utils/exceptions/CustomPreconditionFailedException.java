package service_account.service_account.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class CustomPreconditionFailedException extends RuntimeException{

    public CustomPreconditionFailedException(String message) {
        super(message);
    }
}
