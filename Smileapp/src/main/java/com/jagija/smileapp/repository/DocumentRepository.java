package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
