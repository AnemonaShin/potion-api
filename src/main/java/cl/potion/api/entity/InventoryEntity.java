package cl.potion.api.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

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
 * @since 25-05-2026
 */
@Entity
@Table(name = "Inventory")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inventory_id", unique = true, nullable = false, columnDefinition = "BIGINT")
  BigInteger id;
  @Column(unique = false, nullable = false)
  int space;
  @Column(nullable = false)
  LocalDateTime createAt;
  @Column(nullable = false)
  LocalDateTime updatedAt;
}
