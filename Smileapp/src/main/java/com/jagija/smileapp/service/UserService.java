package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.*;
import com.jagija.smileapp.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserService {
    UserProfileDTO registerPatient(UserRegistrationDTO userRegistrationDTO);
    UserProfileDTO registerDentist(UserRegistrationDTO userRegistrationDTO) throws IOException;
    UserProfileDTO uptadteUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfilebyId(Integer id);
    Integer getAuthenticatedUserIdFromJWT();
    AuthResponseDTO login(LoginDTO loginDTO);
    User getUserbyId(Integer userId);
    boolean validCop(VallidCopDTO copDTO) throws IOException;
    EmergencyResponseDTO createEmergencyInfo(EmergencyRequestDTO emergencyRequestDTO);
    void updateUserImage(Integer userId, MultipartFile image);
}
