package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.AuthResponseDTO;
import com.jagija.smileapp.dto.LoginDTO;
import com.jagija.smileapp.dto.UserProfileDTO;
import com.jagija.smileapp.dto.UserRegistrationDTO;
import com.jagija.smileapp.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    private final EmergencyMapper emergencyMapper;

    public User toUserEntity(UserRegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }

    public UserProfileDTO toUserProfileDTO(User user) {
        UserProfileDTO userProfileDTO =  modelMapper.map(user, UserProfileDTO.class);

        if(user.getPatient()!=null)
        {
            userProfileDTO.setName(user.getPatient().getName());
            userProfileDTO.setLastname(user.getPatient().getLastname());
            userProfileDTO.setGender(user.getPatient().getGender());
            userProfileDTO.setBirthday(user.getPatient().getBirthday());
            userProfileDTO.setPhone(user.getPatient().getPhone());
            if(user.getPatient().getEmergency() != null)
            {
                userProfileDTO.setParent(user.getPatient().getEmergency().getParent());
                userProfileDTO.setPphone(user.getPatient().getEmergency().getPhone());
                userProfileDTO.setPname(user.getPatient().getEmergency().getName());
                userProfileDTO.setPdir(user.getPatient().getEmergency().getDir());
            }

        }
        if(user.getDentist()!=null)
        {
            userProfileDTO.setName(user.getDentist().getName());
            userProfileDTO.setLastname(user.getDentist().getLastname());
            userProfileDTO.setGender(user.getDentist().getGender());
            userProfileDTO.setBirthday(user.getDentist().getBirthday());
            userProfileDTO.setPhone(user.getDentist().getPhone());
            if (user.getDentist().getCicle()!=null)userProfileDTO.setCicle(user.getDentist().getCicle());
            userProfileDTO.setCondition(user.getDentist().getCondition());
            userProfileDTO.setStudyCenter(user.getDentist().getStudyCenter());
        }

        return userProfileDTO;

    }


    public User toUserEntity(LoginDTO loginDTO)
    {
        return modelMapper.map(loginDTO, User.class);
    }

    public AuthResponseDTO toAuthResponseDTO(User user , String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);

        String name = (user.getPatient() != null) ? user.getPatient().getName() :
                (user.getDentist() != null) ? user.getDentist().getName():"ADMIN";
        String lastname = (user.getPatient() != null ) ? user.getPatient().getLastname() :
                (user.getDentist() != null) ? user.getDentist().getLastname():"USER";
        authResponseDTO.setName(name);
        authResponseDTO.setLastname(lastname);
        authResponseDTO.setId(user.getId());
        authResponseDTO.setRole(user.getRole().getName());

        return authResponseDTO;
    }

}
