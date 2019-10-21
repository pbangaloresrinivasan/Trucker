package io.egen.service;

import io.egen.entity.Alert;
import io.egen.exception.BadRequestException;
import io.egen.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    AlertRepository alertRepository;

    @Transactional
    public void insertAlert(Alert alert){
        alertRepository.save(alert);
    }

    @Transactional
    public List<Alert> findAllByVin(String vinNo)
    {
        List<Alert> alert = alertRepository.findAllByVin(vinNo);
        if(alert==null)
            throw new BadRequestException("There are no alerts for the vin "+ vinNo);
        return alert;
    }

    @Transactional
    public List<Alert> allAlerts()
    {
        return (List<Alert>) alertRepository.findAll();
    }
}
