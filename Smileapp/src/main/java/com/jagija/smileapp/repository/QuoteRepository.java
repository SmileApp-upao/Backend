package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuoteRepository extends JpaRepository<Quote, Integer> {

    List<Quote> findByDentist_Id(Integer IdDentista);
    List<Quote> findByPatient_Id(Integer IdPaciente);
}
