package eu.virtusdevelops.ldmontage.controllers;

import eu.virtusdevelops.ldmontage.domain.error.GenericApiError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestErrorAdviceHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        GenericApiError apiError = new GenericApiError(HttpStatus.BAD_REQUEST);
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            apiError.addError(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            apiError.addError(error.getObjectName(), error.getDefaultMessage());
        }
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatusCode status,
                                                                          WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        GenericApiError apiError = new GenericApiError(HttpStatus.BAD_REQUEST, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getConstraintName());
        GenericApiError apiError = new GenericApiError(HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        GenericApiError apiError = new GenericApiError(HttpStatus.BAD_REQUEST, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatusCode status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        GenericApiError apiError = new GenericApiError(HttpStatus.NOT_FOUND, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatusCode status,
                                                                         WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));

        GenericApiError apiError = new GenericApiError(HttpStatus.METHOD_NOT_ALLOWED, builder.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatusCode status,
                                                                     WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        GenericApiError apiError = new GenericApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        GenericApiError apiError = new GenericApiError(
                HttpStatus.INTERNAL_SERVER_ERROR, "error occurred");
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                new GenericApiError(HttpStatus.FORBIDDEN,
                        "Authorization is required"),
                new HttpHeaders(), HttpStatus.FORBIDDEN);
    }





    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElement(
            NoSuchElementException ex, WebRequest request) {
        return new ResponseEntity<>(
                new GenericApiError(HttpStatus.NOT_FOUND, ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<Object> handleIllegalState(
            IllegalStateException ex, WebRequest request) {
        return new ResponseEntity<>(
                new GenericApiError(HttpStatus.NOT_FOUND, ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({org.springframework.security.authentication.BadCredentialsException.class})
    public ResponseEntity<GenericApiError> handleBadCredentials(
            org.springframework.security.authentication.BadCredentialsException ex, WebRequest request) {

        return new ResponseEntity<>(
                new GenericApiError(HttpStatus.BAD_REQUEST, "Bad credentials"),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<GenericApiError> handleNotEnabled(
            DisabledException ex, WebRequest request) {
        return new ResponseEntity<>(
                new GenericApiError(HttpStatus.BAD_REQUEST, "This account has been disabled"),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }

}
