package cl.potion.api.repository;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.potion.api.entity.InventoryEntity;

@Repository
public interface InventoryRepository extends CrudRepository<InventoryEntity, BigInteger> {

}
