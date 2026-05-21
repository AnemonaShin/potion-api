package cl.potion.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cl.potion.api.response.ExceptionResponse;

/**
 * GlobalExceptionHandler for Map Exceptions inside Potion Crafters API.
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 18-05-2026
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handler designed for the custom exception "ServiceException".
   * 
   * @param serviceException Custom Exception.
   * @return Custom response using ResponseEntity.
   */
  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ExceptionResponse> handleGlobalException(ServiceException serviceException) {

    return new ResponseEntity<>(ExceptionResponse.builder()
        .code(serviceException.getCode())
        .message(serviceException.getMessage())
        .timestamp(serviceException.getTimeStamp()).build(), serviceException.getHttpStatus());

  }
}
