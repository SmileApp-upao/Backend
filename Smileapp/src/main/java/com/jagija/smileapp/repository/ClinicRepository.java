package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Clinic;
import com.jagija.smileapp.model.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    boolean existsByLatitudeAndLongitude(String latitude, String longitude);
    boolean existsByDentistas_Id(Integer dentistId);
    boolean existsByName(String name);
    Clinic findByDentistas_Id(Integer dentistId);
}
