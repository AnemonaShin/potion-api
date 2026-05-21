package cl.potion.api.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * Exception List for Potion Crafters designed to List all exceptions.
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 18-05-2026
 */
@Getter
public enum ExceptionList {

  UNFD(HttpStatus.NOT_FOUND, "USER NOT FOUND"),
  USAA(HttpStatus.CONFLICT, "USER ALREADY ACTIVATED"),
  USAD(HttpStatus.CONFLICT, "USER ALREADY DEACTIVATED"),
  USDCU(HttpStatus.NOT_MODIFIED, "CANT UPDATE A DEACTIVATED USER"),
  USDCS(HttpStatus.CONFLICT, "CANT SEARCH A DEACTIVATED USER"),
  USEAT(HttpStatus.CONFLICT, "USER 'EMAIL' ALREADY EXISTS"),
  USUNAT(HttpStatus.CONFLICT, "USER 'USERNAME' ALREADY EXISTS"),
  USDFDP(HttpStatus.CONFLICT, "DATA INTEGRITY VIOLATION: DUPLICATE KEY");

  private final HttpStatus httpStatus;
  private final String message;

  /**
   * Public constructor.
   *
   * @param httpStatus For Response Entity status.
   * @param message    For Default Response message.
   */
  private ExceptionList(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
