package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
import com.jagija.smileapp.dto.HistoryClinicRequestDTO;
import com.jagija.smileapp.dto.HistoryClinicResponseDTO;
import com.jagija.smileapp.mapper.HistoryClinicMapper;
import com.jagija.smileapp.model.entity.HistoryClinic;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.HistoryClinicRepository;
import com.jagija.smileapp.repository.UserRepository;
import com.jagija.smileapp.service.HistoryClinicService;
import com.jagija.smileapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryClinicServiceImpl implements HistoryClinicService {
    private final HistoryClinicRepository historyClinicRepository;
    private final HistoryClinicMapper historyClinicMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public HistoryClinicResponseDTO getHistoryClinic() {
        User user = userService.getUserbyId (userService.getAuthenticatedUserIdFromJWT());
        if(user.getPatient().getHistoryClinic() == null)
        {
            throw new ResourceNotFoundException("El usuario no tiene una historia clinica asociada");
        }
        return historyClinicMapper.convertToDTO(historyClinicRepository.findByPatient_Id(user.getPatient().getId()));
    }

    @Override
    public HistoryClinicResponseDTO addHistoryClinic(HistoryClinicRequestDTO historyClinicRequestDTO) {
        User user = userService.getUserbyId (userService.getAuthenticatedUserIdFromJWT());
        if(user.getPatient().getHistoryClinic() != null)
        {
            throw new IllegalArgumentException("El usuario ya tiene una historia clinica");
        }
        HistoryClinic historyClinic = historyClinicMapper.convertToEntity(historyClinicRequestDTO);
        historyClinic.setPatient(user.getPatient());
        historyClinic= historyClinicRepository.save(historyClinic);
        user.getPatient().setHistoryClinic(historyClinic);
        userRepository.save(user);
        return historyClinicMapper.convertToDTO(historyClinic);
    }

    @Override
    public HistoryClinicResponseDTO updateHistoryClinic(HistoryClinicRequestDTO historyClinicRequestDTO) {
        User user = userService.getUserbyId (userService.getAuthenticatedUserIdFromJWT());
        if(user.getPatient().getHistoryClinic() == null)
        {
            throw new ResourceNotFoundException("El usuario no tiene una historia clinica asociada");
        }
        HistoryClinic ActuallhistoryClinic=historyClinicRepository.findByPatient_Id(user.getPatient().getId());
        if(historyClinicRequestDTO.getBloodType()!=null)ActuallhistoryClinic.setBloodType(historyClinicRequestDTO.getBloodType());
        if(historyClinicRequestDTO.getRh()!=null)ActuallhistoryClinic.setRh(historyClinicRequestDTO.getRh());
        if(historyClinicRequestDTO.getCivilState()!=null)ActuallhistoryClinic.setCivilState(historyClinicRequestDTO.getCivilState());
        if(historyClinicRequestDTO.getBirthPlace()!=null)ActuallhistoryClinic.setBirthPlace(historyClinicRequestDTO.getBirthPlace());
        if(historyClinicRequestDTO.getDir()!=null)ActuallhistoryClinic.setDir(historyClinicRequestDTO.getDir());
        if(historyClinicRequestDTO.getStudyGrade()!=null)ActuallhistoryClinic.setStudyGrade(historyClinicRequestDTO.getStudyGrade());
        if(historyClinicRequestDTO.getProfession()!=null)ActuallhistoryClinic.setProfession(historyClinicRequestDTO.getProfession());
        if(historyClinicRequestDTO.getOccupation()!=null)ActuallhistoryClinic.setOccupation(historyClinicRequestDTO.getOccupation());
        if(historyClinicRequestDTO.getWorkCenter()!=null)ActuallhistoryClinic.setWorkCenter(historyClinicRequestDTO.getWorkCenter());
        if(historyClinicRequestDTO.getWorkDir()!=null)ActuallhistoryClinic.setWorkDir(historyClinicRequestDTO.getWorkDir());
        if(historyClinicRequestDTO.getReligion()!=null)ActuallhistoryClinic.setReligion(historyClinicRequestDTO.getReligion());
        if(historyClinicRequestDTO.getHome()!=null)ActuallhistoryClinic.setHome(historyClinicRequestDTO.getHome());

        return historyClinicMapper.convertToDTO(historyClinicRepository.save(ActuallhistoryClinic));
    }
}
