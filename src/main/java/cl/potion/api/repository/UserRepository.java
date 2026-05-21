package cl.potion.api.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.potion.api.entity.UserEntity;

/**
 * User Repository for User consults to Database.
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 10-05-2026
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, BigInteger> {

  /**
   * Search by Username.
   * 
   * @param username Username to search.
   * @return User searched.
   */
  UserEntity searchByUsername(String username);

  /**
   * Search By Id.
   * 
   * @param userId ID to search.
   * @return User searched.
   */
  UserEntity searchById(BigInteger userId);

  /**
   * Search All User on state "active".
   * 
   * @param pageable Pageable object to "control" data.
   * @return Pageable response with users searched.
   */
  Page<UserEntity> searchAllByActiveTrue(Pageable pageable);
}
