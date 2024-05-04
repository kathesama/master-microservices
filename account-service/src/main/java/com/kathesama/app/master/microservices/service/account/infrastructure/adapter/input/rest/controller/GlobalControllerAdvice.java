package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.master.microservices.service.common.domain.exception.CustomerAlreadyExistsException;
import com.kathesama.app.master.microservices.service.common.domain.exception.ProductNotFoundException;
import com.kathesama.app.master.microservices.service.common.domain.exception.ResourceNotFoundException;
import com.kathesama.app.master.microservices.service.common.domain.exception.UserNotFoundException;
import com.kathesama.app.master.microservices.service.common.domain.model.ErrorResponse;
import com.kathesama.app.master.microservices.service.common.util.common.ErrorCatalog;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.kathesama.app.master.microservices.service.common.util.common.ErrorCatalog.*;

@RestControllerAdvice
public class GlobalControllerAdvice {
    private static final Map<Class<? extends Exception>, HttpStatus> STATUS_CODE_MAPPING = new HashMap<>();
    static {
        STATUS_CODE_MAPPING.put(ProductNotFoundException.class, HttpStatus.NOT_FOUND);
        STATUS_CODE_MAPPING.put(UserNotFoundException.class, HttpStatus.NOT_FOUND);
        STATUS_CODE_MAPPING.put(ResourceNotFoundException.class, HttpStatus.NOT_FOUND);
        STATUS_CODE_MAPPING.put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
        STATUS_CODE_MAPPING.put(CustomerAlreadyExistsException.class, HttpStatus.BAD_REQUEST);
        STATUS_CODE_MAPPING.put(AccessDeniedException.class, HttpStatus.UNAUTHORIZED);
    }
    private static final Map<Class<? extends Exception>, ErrorCatalog> ERROR_CODE_MAPPING = new HashMap<>();
    static {
        ERROR_CODE_MAPPING.put(ProductNotFoundException.class, PRODUCT_NOT_FOUND);
        ERROR_CODE_MAPPING.put(UserNotFoundException.class, USER_NOT_FOUND);
        ERROR_CODE_MAPPING.put(MethodArgumentNotValidException.class, INVALID_PARAMS);
        ERROR_CODE_MAPPING.put(ConstraintViolationException.class, INVALID_PARAMS);
        ERROR_CODE_MAPPING.put(AccessDeniedException.class, ACCESS_DENIED);
        ERROR_CODE_MAPPING.put(CustomerAlreadyExistsException.class, CUSTOMER_ALREADY_EXISTS);
        ERROR_CODE_MAPPING.put(ResourceNotFoundException.class, RESOURCE_NOT_FOUND);
    }

    private static ErrorResponse createErrorResponse(ErrorCatalog errorType) {
        return ErrorResponse.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /************* INTERNAL ERROR HANDLER */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericError(Exception exception) {

        ErrorResponse errorResponse = createErrorResponse(GENERIC_ERROR);
        errorResponse.setDetails(Collections.singletonList(exception.getMessage()));
        return errorResponse;
    }

    /************* Products, Users, access and Given params ERROR HANDLER */
    @ExceptionHandler({
            AccessDeniedException.class,
            ProductNotFoundException.class,
            UserNotFoundException.class,
            ResourceNotFoundException.class,
            CustomerAlreadyExistsException.class,
            MethodArgumentNotValidException.class,
            HandlerMethodValidationException.class,
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
    })
    public ResponseEntity<ErrorResponse> handleGeneralCustomizedExceptions(Exception exception) {
        ErrorCatalog response = ERROR_CODE_MAPPING.getOrDefault(exception.getClass(), GENERIC_ERROR);
        HttpStatus statusCode = STATUS_CODE_MAPPING.getOrDefault(exception.getClass(), HttpStatus.BAD_REQUEST);

        ErrorResponse errorResponse = createErrorResponse(response);

        if (statusCode.equals(HttpStatus.BAD_REQUEST)) {
            List<String> details = new ArrayList<String>();

            if (exception instanceof MethodArgumentNotValidException methodargumentnotvalidexception){
                BindingResult result = methodargumentnotvalidexception.getBindingResult();
                details = result
                        .getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList());
            }else{
                details.add(filterMessage(exception.getMessage()));
            }

            errorResponse.setDetails(details);
        }

        return new ResponseEntity<>(errorResponse, statusCode);
    }

    private String filterMessage(String input){
        String[] patterns = {": public", "public", "org.", "com."};

        for (String pattern : patterns) {
            Pattern p = Pattern.compile(Pattern.quote(pattern));
            Matcher m = p.matcher(input);
            if (m.find()) {
                input = input.substring(0, m.start());
                break;
            }
        }

        return input;
    }
}
