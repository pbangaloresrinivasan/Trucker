package io.egen.repository;

import io.egen.entity.Alert;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlertRepository extends CrudRepository<Alert, String> {

    List<Alert> findAllByVin(String vinNo);
}
