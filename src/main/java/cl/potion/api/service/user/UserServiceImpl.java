package cl.potion.api.service.user;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cl.potion.api.entity.UserEntity;
import cl.potion.api.exception.ExceptionList;
import cl.potion.api.exception.ServiceException;
import cl.potion.api.repository.UserRepository;
import cl.potion.api.request.UserRequest;
import cl.potion.api.response.DefaultResponse;
import cl.potion.api.util.ExceptionUtil;
import cl.potion.api.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements the high interface of service for uses on users managements.
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 10-05-2026
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
  private final LocalDateTime localNowCl = LocalDateTime.now(ZoneId.of("America/Santiago"));

  UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  public DefaultResponse registerUser(UserRequest body) throws ServiceException {
    try {
      repository.saveAndFlush(UserEntity.builder()
          .username(body.getUsername())
          .password(PasswordUtil.passwordEncrypt(body.getPassword()))
          .email(body.getEmail())
          .createAt(
              localNowCl)
          .updatedAt(
              localNowCl)
          .active(true)
          .build());
      return DefaultResponse.builder().code("200").message("USER REGISTERED").build();
    } catch (DataIntegrityViolationException daex) {
      log.error("DataIntegrityViolationException inside registerUser, ex: {}", daex);
      throw new ServiceException(ExceptionUtil.duplicateConstraintError(daex));
    } catch (Exception ex) {
      log.error("Exception inside registerUser, ex: {}", ex);
      throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  public Page<UserEntity> getAllUsers(PageRequest pageRequest) throws ServiceException {
    try {
      return repository.searchAllByActiveTrue(pageRequest);
    } catch (Exception ex) {
      log.error("Exception inside getAllUsers, ex: {}", ex);
      throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  public DefaultResponse searchByUsername(String username) throws ServiceException {
    try {
      var userSearched = repository.searchByUsername(username);

      if (userSearched == null) {
        throw new ServiceException(ExceptionList.UNFD);
      }

      if (Boolean.FALSE == userSearched.getActive()) {
        throw new ServiceException(ExceptionList.USDCS);
      }

      return DefaultResponse.builder().code("200").message("USER SEARCHED").response(
          userSearched)
          .build();
    } catch (ServiceException excp) {
      log.error("ServiceException inside searchByUsername, ex: {}", excp);
      throw new ServiceException(excp.getHttpStatus(), excp.getCode(), excp.getMessage());
    } catch (Exception ex) {
      log.error("Exception inside searchByUsername, ex: {}", ex);
      throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

  }

  public DefaultResponse deactivateUser(BigInteger userId) throws ServiceException {
    try {

      var user = repository.searchById(userId);

      if (user == null) {
        throw new ServiceException(ExceptionList.UNFD);
      }

      if (Boolean.FALSE == user.getActive()) {
        throw new ServiceException(ExceptionList.USAD);
      }

      user.setActive(false);
      user.setUpdatedAt(localNowCl);

      repository.saveAndFlush(user);

      return DefaultResponse.builder().code("200").message("USER DEACTIVATED").build();

    } catch (ServiceException excp) {
      log.error("ServiceException inside deactivateUser, ex: {}", excp);
      throw new ServiceException(excp.getHttpStatus(), excp.getCode(), excp.getMessage());
    } catch (Exception ex) {
      log.error("Exception inside deactivateUser, ex: {}", ex);
      throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

  }

  public DefaultResponse activateUser(BigInteger userId) throws ServiceException {
    try {
      var user = repository.searchById(userId);

      if (user == null) {
        throw new ServiceException(ExceptionList.UNFD);
      }

      if (Boolean.TRUE == user.getActive()) {
        throw new ServiceException(ExceptionList.USAA);
      }

      user.setActive(true);
      user.setUpdatedAt(localNowCl);

      repository.saveAndFlush(user);

      return DefaultResponse.builder().code("200").message("USER ACTIVATED").build();
    } catch (ServiceException excp) {
      log.error("ServiceException inside activateUser, ex: {}", excp);
      throw new ServiceException(excp.getHttpStatus(), excp.getCode(), excp.getMessage());
    } catch (Exception ex) {
      log.error("Exception inside activateUser, ex: {}", ex);
      throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

  }

  public DefaultResponse updateUser(BigInteger userId, UserRequest body) throws ServiceException {
    try {
      var user = repository.searchById(userId);

      if (user == null) {
        throw new ServiceException(ExceptionList.UNFD);
      }

      if (Boolean.FALSE == user.getActive()) {
        throw new ServiceException(ExceptionList.USDCU);
      }

      user.setUsername(body.getUsername() != null ? body.getUsername() : user.getUsername());
      user.setPassword(
          body.getPassword() != null ? PasswordUtil.passwordEncrypt(body.getPassword()) : user.getPassword());
      user.setEmail(body.getEmail() != null ? body.getEmail() : user.getEmail());
      user.setUpdatedAt(localNowCl);

      repository.saveAndFlush(user);

      return DefaultResponse.builder().code("200").message("USER UPDATED").response(user).build();
    } catch (DataIntegrityViolationException daex) {
      log.error("DataIntegrityViolationException inside updateUser, ex: {}", daex);
      throw new ServiceException(ExceptionUtil.duplicateConstraintError(daex));
    } catch (ServiceException excp) {
      log.error("ServiceException inside updateUser, ex: {}", excp);
      throw new ServiceException(excp.getHttpStatus(), excp.getCode(), excp.getMessage());
    } catch (Exception ex) {
      log.error("Exception inside updateUser, ex: {}", ex);
      throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

}
