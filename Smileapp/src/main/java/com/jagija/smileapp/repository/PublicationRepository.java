package com.jagija.smileapp.repository;

import com.jagija.smileapp.dto.PublicationResponseDTO;
import com.jagija.smileapp.model.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Integer> {

    //@Query("SELECT p FROM Publication p WHERE p.dentist.id = :dentistId")
    //List<Publication> findPublicationsByDentistId(@Param("dentistId") Integer dentistId);

    List<Publication> findPublicationsByDentist_Id(Integer dentistId);


}
