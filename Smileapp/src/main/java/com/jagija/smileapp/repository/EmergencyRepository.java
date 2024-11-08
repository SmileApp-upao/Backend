package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {
}
