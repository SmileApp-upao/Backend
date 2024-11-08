package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.AuthResponseDTO;
import com.jagija.smileapp.dto.LoginDTO;
import com.jagija.smileapp.dto.UserProfileDTO;
import com.jagija.smileapp.dto.UserRegistrationDTO;
import com.jagija.smileapp.model.entity.User;


public interface UserService {
    UserProfileDTO registerPatient(UserRegistrationDTO userRegistrationDTO);
    UserProfileDTO registerDentist(UserRegistrationDTO userRegistrationDTO);
    UserProfileDTO uptadteUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfilebyId(Integer id);
    Integer getAuthenticatedUserIdFromJWT();
    AuthResponseDTO login(LoginDTO loginDTO);
    User getUserbyId(Integer userId);
}
