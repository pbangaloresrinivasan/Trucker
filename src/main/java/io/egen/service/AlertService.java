package io.egen.service;

import io.egen.entity.Alert;

import java.util.List;

public interface AlertService {

    void insertAlert(Alert alert);
    List<Alert> findAllByVin(String vinNo);
    List<Alert> allAlerts();
}
