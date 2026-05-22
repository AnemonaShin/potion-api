package cl.potion.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.potion.api.entity.UserEntity;
import cl.potion.api.exception.ServiceException;
import cl.potion.api.dto.request.UserRequest;
import cl.potion.api.dto.response.DefaultResponse;
import cl.potion.api.service.user.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Rest Controller for Potion Crafters Users API
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 10-05-2026
 */
@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

  UserService userService;

  /**
   * Public Constructor of the class.
   *
   * @param userService Service designed for user needs.
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Post Method for the registry of new users on system.
   *
   * @param body Request Object for Users.
   * @return Response Entity with "DefaultResponse" Object.
   * @throws ServiceException Custom throw for errors on the service.
   */
  @Tag(name = "register", description = "Endpoints designed to registers.")
  @ApiResponse(description = "Responds a custom response object.")
  @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DefaultResponse> registerUser(
      @RequestBody UserRequest body)
      throws ServiceException {
    DefaultResponse response = userService.registerUser(body);
    return ResponseEntity.ok().body(response);
  }

  /**
   * Get Method for search an user using his username.
   *
   * @param username for the searching.
   * @return Response Entity with "DefaultResponse" Object.
   * @throws ServiceException Custom throw for errors on the service.
   */
  @Tag(name = "search", description = "Endpoints designed to searchs.")
  @GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponse(description = "Responds a custom response object with user searched data.")
  public ResponseEntity<DefaultResponse> searchByUsername(
      @PathVariable(name = "username", required = true) String username) throws ServiceException {

    DefaultResponse response = userService.searchByUsername(username);
    return ResponseEntity.ok().body(response);
  }

  /**
   * Get Method designed to search all users on system prioriticing active ones
   * and using page to do that.
   *
   * @param page Int for page positioning.
   * @param size Int for the size of the list displayed.
   * @return A page of active users.
   * @throws ServiceException Custom throw for errors on the service.
   */
  @Tag(name = "search", description = "Endpoints designed to searchs.")
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponse(description = "Responds a paginated object with users data.")
  public Page<UserEntity> getAllUsers(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) throws ServiceException {
    return userService.getAllUsers(PageRequest.of(page, size));
  }

  /**
   * Delete Method designed to deactivate an active user.
   *
   * @param userId Id of the user to deactivate.
   * @return Response Entity with "DefaultResponse" Object.
   * @throws ServiceException Custom throw for errors on the service.
   */
  @Tag(name = "delete", description = "Endpoints designed to delete or deactivate.")
  @DeleteMapping(path = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponse(description = "Responds a custom response object.")
  public ResponseEntity<DefaultResponse> deactivateUser(
      @PathVariable(name = "user_id") BigInteger userId) throws ServiceException {
    userService.deactivateUser(userId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Patch Method designed to re activate a deactivated user.
   *
   * @param userId Id of the user to activate.
   * @return Response Entity with "DefaultResponse" Object.
   * @throws ServiceException Custom throw for errors on the service.
   */
  @Tag(name = "update", description = "Endpoints designed to update data.")
  @PatchMapping(path = "/{user_id}/activate", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponse(description = "Responds a custom response object with users data modified.")
  public ResponseEntity<DefaultResponse> activateUser(@PathVariable(name = "user_id") BigInteger userId)
      throws ServiceException {
    userService.activateUser(userId);
    return ResponseEntity.noContent().build();
  }

  /**
   * Put Method for the update of an user.
   *
   * @param userId Id of the user to update.
   * @param body   Request Object for Users.
   * @return Response Entity with "DefaultResponse" Object.
   * @throws ServiceException Custom throw for errors on the service.
   */
  @Tag(name = "update", description = "Endpoints designed to update data.")
  @PutMapping(path = "/{user_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponse(description = "Responds a custom response object with users data modified.")
  public ResponseEntity<DefaultResponse> updateUser(
      @PathVariable(name = "user_id") BigInteger userId,
      @RequestBody UserRequest body) throws ServiceException {
    return ResponseEntity.ok().body(userService.updateUser(userId, body));
  }

}
