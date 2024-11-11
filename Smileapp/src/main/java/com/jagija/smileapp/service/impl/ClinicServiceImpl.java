package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
import com.jagija.smileapp.Exceptions.UserNotFoundException;
import com.jagija.smileapp.dto.ClinicRequestDTO;
import com.jagija.smileapp.dto.ClinicResponseDTO;
import com.jagija.smileapp.mapper.ClinicMapper;
import com.jagija.smileapp.model.entity.Clinic;
import com.jagija.smileapp.model.entity.Dentist;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.ClinicRepository;
import com.jagija.smileapp.service.ClinicService;
import com.jagija.smileapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;
    private final UserService userService;
    @Override
    public ClinicResponseDTO addClinic(ClinicRequestDTO clinic) {
        User user =userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        Dentist dentist = user.getDentist();
        if(user.getRole().getName().equals("PATIENT"))
        {
            throw new IllegalArgumentException("Un paciente no puede asociarse a una clinica");
        }
        if(dentist == null){
            throw new UserNotFoundException("Dentista no encontrado");
        }
        if(clinicRepository.existsByName(clinic.getName()))
        {
            throw new IllegalArgumentException("La clinica ya existente");
        }
        if(clinicRepository.existsByLatitudeAndLongitude(clinic.getLatitude(), clinic.getLongitude()))
        {
            throw new IllegalArgumentException("La clinica ya existente");
        }
        if(clinicRepository.existsByDentistas_Id(dentist.getId()))
        {
            throw new IllegalArgumentException("El dentista ya tiene una clinica asociada");
        }
        Clinic clinica=clinicMapper.convertToEntity(clinic,user.getDentist().getId());
        List<Dentist> dentistas=new ArrayList<>();
        dentistas.add(dentist);
        clinica.setDentistas(dentistas);

        return clinicMapper.convertToDTO(clinicRepository.save(clinica));
    }

    @Override
    public List<ClinicResponseDTO> getallClinics() {
        return  clinicMapper.convertToListDTO(clinicRepository.findAll());
    }

    @Override
    public ClinicResponseDTO getClinicById(Integer id) {
        Clinic clinic = clinicRepository.findById(id).orElse(null);
        if (clinic == null) {
            throw new ResourceNotFoundException("Clinica no encontrada");
        }
        return clinicMapper.convertToDTO(clinic);
    }

    @Override
    public ClinicResponseDTO updateInfoClinic(ClinicRequestDTO clinic) {
        User user =userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        Clinic actuallClinic = clinicRepository.findByDentistas_Id(user.getDentist().getId());
        if(clinicRepository.existsByName(clinic.getName()))
        {
            throw new IllegalArgumentException("Ya existe una clinica con ese nombre");
        }
        if(clinic.getName()!=null)actuallClinic.setName(clinic.getName());
        if(clinicRepository.existsByLatitudeAndLongitude(clinic.getLatitude(), clinic.getLongitude()))
        {
            throw new IllegalArgumentException("Ya existe una clinica con esa ubicacion");
        }
        if(clinic.getLatitude()!=null)actuallClinic.setLatitude(clinic.getLatitude());
        if(clinic.getLongitude()!=null)actuallClinic.setLongitude(clinic.getLongitude());
        if(clinic.getOpenHour()!=null)actuallClinic.setOpenHour(clinic.getOpenHour());
        if(clinic.getCloseHour()!=null)actuallClinic.setCloseHour(clinic.getCloseHour());
        if(clinic.getOpenDays()!=null)actuallClinic.setOpenDays(clinic.getOpenDays());
        if (clinic.getAddress()!=null)actuallClinic.setAddress(clinic.getAddress());
        if(clinic.getDesc()!=null)actuallClinic.setDesc(clinic.getDesc());
        if(clinic.getTelf()!=null)actuallClinic.setTelf(clinic.getTelf());
        if(clinic.getEmail()!=null)actuallClinic.setEmail(clinic.getEmail());
        clinicRepository.save(actuallClinic);
        return clinicMapper.convertToDTO(actuallClinic);
    }
}
