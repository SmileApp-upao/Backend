package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DentistRepository extends JpaRepository<Dentist, Integer> {
    Optional<Dentist> findByNameAndLastname(String nombre, String last_name);
    boolean existsByNameAndLastname(String name, String last_name);
    boolean existsByNameAndLastnameAndUserIdNot(String email, String last_name,int userId);
}
