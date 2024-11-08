package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Dentist;
import com.jagija.smileapp.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByNameAndLastname(String nombre, String last_name);
    boolean existsByNameAndLastname(String name, String last_name);
    boolean existsByNameAndLastnameAndUserIdNot(String email, String last_name,int userId);
}
