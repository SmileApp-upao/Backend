package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByDentist_Id(Integer id);
}
