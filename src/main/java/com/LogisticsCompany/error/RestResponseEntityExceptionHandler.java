package com.LogisticsCompany.error;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(LogisticCompanyNotFoundException.class)
    public ResponseEntity<ErrorMessage> logisticCompanyNotFoundException(LogisticCompanyNotFoundException logisticCompanyNotFoundException, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, logisticCompanyNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(OfficeNotFoundException.class)
    public ResponseEntity<ErrorMessage> officeNotFoundException(OfficeNotFoundException officeNotFoundException, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, officeNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorMessage> invalidStatusException(InvalidStatusException invalidStatusException, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, invalidStatusException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(CompanyNoOfficesException.class)
    public ResponseEntity<ErrorMessage> companyNoOfficesException(CompanyNoOfficesException companyNoOfficesException, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, companyNoOfficesException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(OrderCreationValidationException.class)
    public ResponseEntity<ErrorMessage> handleOrderCreationValidationException(
            OrderCreationValidationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(EntityAlreadyExistsInDbException.class)
    public ResponseEntity<ErrorMessage> handleEntityAlreadyExistsInDbException(
            EntityAlreadyExistsInDbException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(DeliveryStatusException.class)
    public ResponseEntity<ErrorMessage> handleDeliveryStatusException(
            DeliveryStatusException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
