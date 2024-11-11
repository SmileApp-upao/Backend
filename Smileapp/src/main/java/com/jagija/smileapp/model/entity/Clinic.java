package com.jagija.smileapp.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "clinic")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private List<Dentist> dentistas;

    @Column(name = "openHour", nullable = false)
    private LocalTime openHour;

    @Column(name = "closeHour", nullable = false)
    private LocalTime closeHour;

    @Column(name = "openDays", nullable = false)
    private String openDays;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = false)
    private String desc;

    @Column(name = "telf", nullable = false)
    private String telf;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "latitude", nullable = false)
    private String latitude;
    @Column(name = "longitude", nullable = false)

    private String longitude;


    public void setOpenDaysFromList(List<DayOfWeek> openDaysList) {
        this.openDays = openDaysList.stream()
                .map(DayOfWeek::name)
                .collect(Collectors.joining(","));
    }
    public static List<DayOfWeek> convertStringsToDayOfWeek(List<String> days) {
        return days.stream()
                .map(day -> DayOfWeek.valueOf(day.toUpperCase()))
                .collect(Collectors.toList());
    }

    public static boolean isOpenOnDay(List<String> openDays, DayOfWeek day) {
        List<DayOfWeek> daysOfWeek = convertStringsToDayOfWeek(openDays);
        return daysOfWeek.contains(day);
    }
}
