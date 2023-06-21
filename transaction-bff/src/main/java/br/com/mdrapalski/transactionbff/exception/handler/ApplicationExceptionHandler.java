package br.com.mdrapalski.transactionbff.exception.handler;

import br.com.mdrapalski.transactionbff.exception.response.ErrorResponse;
import br.com.mdrapalski.transactionbff.exception.exceptions.NotFoundException;
import br.com.mdrapalski.transactionbff.exception.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthorized(UnauthorizedException exception) {
        log.error("Unauthorized error", exception);
        return new ErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundError(NotFoundException exception) {
        log.error("Resource not found error", exception);
        return new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalError(Exception exception) {
        log.error("Internal server error", exception);
        return new ErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
