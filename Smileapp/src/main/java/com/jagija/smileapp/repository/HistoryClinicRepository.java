package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Emergency;
import com.jagija.smileapp.model.entity.HistoryClinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryClinicRepository extends JpaRepository<HistoryClinic, Integer> {
    HistoryClinic findByPatient_Id(Integer userId);
}
