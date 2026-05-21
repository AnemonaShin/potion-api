package cl.potion.api.entity;

import java.math.BigInteger;
import java.util.Date;

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
 * Potion Entity for Potion Crafters
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 16-05-2026
 */
@Entity
@Table(name = "Potion")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PotionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "potion_id", unique = true, nullable = false, columnDefinition = "BIGINT")
  BigInteger id;
  @Column(unique = true, nullable = false)
  String name;
  @Column(nullable = false)
  String type;
  @Column(nullable = false)
  Date createAt;
  @Column(nullable = false)
  Date updatedAt;
}
