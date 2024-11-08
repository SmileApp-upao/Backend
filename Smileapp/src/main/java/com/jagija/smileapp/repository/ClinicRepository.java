package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
}
