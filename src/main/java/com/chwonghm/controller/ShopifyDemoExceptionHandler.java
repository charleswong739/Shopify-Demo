package com.chwonghm.controller;

import com.chwonghm.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Iterator;

/**
 * Exception handler for Saguaro. This class provides methods that intercept exceptions
 * thrown by controllers, and responds accordingly.
 * <p>
 * Handled exceptions include:
 * <ul>
 *     <li>ResourceNotFoundException
 * </ul>
 *
 * @author Charles Wong
 */
@ControllerAdvice
public class ShopifyDemoExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler for any ResourceNotFoundException. This exception is thrown when a
     * requests attempts to access a resource that does not exist
     * <p>
     * Returns a 404 NOT_FOUND with the exception message.
     *
     * @param e       the ResourceNotFoundException to be handled
     * @param request the request which generated this exception
     * @return a response with 404 NOT_FOUND status
     * @see ResourceNotFoundException
     */
    @ExceptionHandler(value = {
            ResourceNotFoundException.class
    })
    ResponseEntity<Object> handle(ResourceNotFoundException e, WebRequest request) {

        return handleExceptionInternal(e,
                e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class
    })
    ResponseEntity<Object> handle(ConstraintViolationException e, WebRequest request) {

        StringBuilder builder = new StringBuilder(50);

        Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator();
        while(it.hasNext()) {
            ConstraintViolation<?> violation = it.next();

            String field = "";

            for (Path.Node node : violation.getPropertyPath()) {
                field = node.getName();
            }

            String msg = violation.getMessage();

            builder.append(field).append(" ").append(msg);

            if (it.hasNext()) {
                builder.append(", ");
            }
        }

        return handleExceptionInternal(e,
                builder.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
