package com.jagija.smileapp.repository;

import com.jagija.smileapp.model.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface QuoteRepository extends JpaRepository<Quote, Integer> {

    List<Quote> findByDentist_Id(Integer IdDentista);
    List<Quote> findByPatient_Id(Integer IdPaciente);
    @Query("SELECT a FROM Quote a WHERE a.dentist.id = :dentistId AND a.date = :date AND " +
            "((a.hour <= :endTime AND a.endtime > :startTime))")
    List<Quote> findAppointmentsByDentistAndTime(@Param("dentistId") int dentistId,
                                                       @Param("date") LocalDate date,
                                                       @Param("startTime") LocalTime startTime,
                                                       @Param("endTime") LocalTime endTime);
}
