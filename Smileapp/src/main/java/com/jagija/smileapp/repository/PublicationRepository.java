package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Integer> {
}
