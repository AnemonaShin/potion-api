package cl.potion.api.service.user;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cl.potion.api.entity.UserEntity;
import cl.potion.api.exception.ServiceException;
import cl.potion.api.request.UserRequest;
import cl.potion.api.response.DefaultResponse;

/**
 * Interface for Users management.
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 10-05-2026
 */
@Service
public interface UserService {

  public DefaultResponse registerUser(UserRequest body) throws ServiceException;

  public Page<UserEntity> getAllUsers(PageRequest pageRequest) throws ServiceException;

  public DefaultResponse searchByUsername(String username) throws ServiceException;

  public DefaultResponse deactivateUser(BigInteger userId) throws ServiceException;

  public DefaultResponse activateUser(BigInteger userId) throws ServiceException;

  public DefaultResponse updateUser(BigInteger userId, UserRequest body) throws ServiceException;

}
