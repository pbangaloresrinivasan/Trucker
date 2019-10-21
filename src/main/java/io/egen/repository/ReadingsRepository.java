package io.egen.repository;

import io.egen.entity.Readings;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReadingsRepository extends CrudRepository<Readings, String> {
    Optional<Readings> findByVin(String vinNo);
}
