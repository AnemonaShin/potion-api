package cl.potion.api.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * Custom Exception that extends Exception itself for Potion Crafters.
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 18-05-2026
 */
@Getter
public class ServiceException extends Exception {

  private static final String TIME_ZONE = "America/Santiago";

  final String code;
  final HttpStatus httpStatus;
  final String message;
  final LocalDateTime timeStamp;

  /**
   * Public Constructor - Just Status and Message.
   * 
   * @param httpStatus For status on exception.
   * @param message    For message on exception.
   */
  public ServiceException(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.code = String.valueOf(httpStatus.value());
    this.message = message;
    this.timeStamp = LocalDateTime.now(ZoneId.of(TIME_ZONE));
  }

  /**
   * Public Constructor - Just Status, Code and Message.
   * 
   * @param httpStatus For status on exception.
   * @param code       For code on exception.
   * @param message    For message on exception.
   */
  public ServiceException(HttpStatus httpStatus, String code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
    this.timeStamp = LocalDateTime.now(ZoneId.of(TIME_ZONE));
  }

  /**
   * Public Constructor - Just ExceptionList Object.
   * 
   * @param exceptionList Enum created to contain diferent default exceptions.
   */
  public ServiceException(ExceptionList exceptionList) {
    this.httpStatus = exceptionList.getHttpStatus();
    this.code = String.valueOf(exceptionList.getHttpStatus().value());
    this.message = exceptionList.getMessage();
    this.timeStamp = LocalDateTime.now(ZoneId.of(TIME_ZONE));
  }
}
