package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.ClinicRequestDTO;
import com.jagija.smileapp.dto.ClinicResponseDTO;
import com.jagija.smileapp.model.entity.Clinic;
import com.jagija.smileapp.model.entity.Dentist;

import com.jagija.smileapp.repository.DentistRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class ClinicMapper {

    private final ModelMapper modelMapper;
    private final DentistRepository dentistRepository;
    private final DentistMapper dentistMapper;

    public Clinic convertToEntity(ClinicRequestDTO clinicRequestDTO) {
        // Mapeo básico de ClinicRequestDTO a Clinic utilizando ModelMapper
        Clinic clinic = modelMapper.map(clinicRequestDTO, Clinic.class);


        DayOfWeek[] openDays = Arrays.stream(clinicRequestDTO.getOpenDays().split(","))
                .map(String::trim)  // Eliminar espacios extra
                .map(DayOfWeek::valueOf) // Convertir cada día de la semana a DayOfWeek
                .toArray(DayOfWeek[]::new);

        // Convertir el array DayOfWeek[] a una lista de DayOfWeek
        List<DayOfWeek> openDaysList = Arrays.asList(openDays);

        // Establecer los días abiertos en la entidad
        clinic.setOpenDaysFromList(openDaysList);

        // Crear la lista de dentistas a partir de los IDs
        List<Dentist> dentists = new ArrayList<>();
        for (Integer dentistId : clinicRequestDTO.getDentistIds()) {
            Dentist dentist = dentistRepository.findById(dentistId).orElse(null);
            if (dentist != null) {
                dentists.add(dentist);
            }
        }

        // Establecer la lista de dentistas en la entidad
        clinic.setDentistas(dentists);

        // Devolver la entidad Clinic
        return clinic;
    }

    public ClinicResponseDTO convertToDTO(Clinic clinic) {

        ClinicResponseDTO responseDTO =  modelMapper.map(clinic, ClinicResponseDTO.class);
        DayOfWeek[] openDays = Arrays.stream(clinic.getOpenDays().split(","))
                .map(String::trim)  // Eliminar espacios extra
                .map(DayOfWeek::valueOf) // Convertir cada día de la semana a DayOfWeek
                .toArray(DayOfWeek[]::new);
        List<DayOfWeek> openDaysList = Arrays.asList(openDays);
        responseDTO.setOpenDays(openDaysList);
        responseDTO.setDentists(dentistMapper.convertToListDTO(clinic.getDentistas()));
        return responseDTO;
    }

    public List<ClinicResponseDTO> convertToListDTO(List<Clinic> clinics) {
        return clinics.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
