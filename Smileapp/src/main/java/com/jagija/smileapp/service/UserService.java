package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.*;
import com.jagija.smileapp.model.entity.User;

import java.io.IOException;


public interface UserService {
    UserProfileDTO registerPatient(UserRegistrationDTO userRegistrationDTO);
    UserProfileDTO registerDentist(UserRegistrationDTO userRegistrationDTO) throws IOException;
    UserProfileDTO uptadteUserProfile(Integer id, UserProfileDTO userProfileDTO);
    UserProfileDTO getUserProfilebyId(Integer id);
    Integer getAuthenticatedUserIdFromJWT();
    AuthResponseDTO login(LoginDTO loginDTO);
    User getUserbyId(Integer userId);
    boolean validCop(VallidCopDTO copDTO) throws IOException;
}
