package cl.potion.api.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * User Entity for Potion Crafters
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 10-05-2026
 */
@Entity
@Table(name = "Game_User")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "BIGINT")
  BigInteger id;
  @Column(unique = true, nullable = false)
  String username;
  @JsonIgnore
  @Column(nullable = false)
  String password;
  @Column(unique = true, nullable = false)
  String email;
  @Column(nullable = false)
  LocalDateTime createAt;
  @Column(nullable = false)
  LocalDateTime updatedAt;
  @Column(nullable = false)
  Boolean active;

}
