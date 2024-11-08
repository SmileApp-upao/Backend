package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.UserNotFoundException;
import com.jagija.smileapp.Security.TokenProvider;
import com.jagija.smileapp.Security.UserPrincipal;
import com.jagija.smileapp.dto.*;
import com.jagija.smileapp.mapper.EmergencyMapper;
import com.jagija.smileapp.mapper.UserMapper;
import com.jagija.smileapp.model.entity.*;
import com.jagija.smileapp.repository.*;
import com.jagija.smileapp.service.EmergencyService;
import com.jagija.smileapp.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmergencyService emergencyService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DentistRepository dentistRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired UserMapper userMapper;
    @Autowired
    private EmergencyMapper emergencyMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;


    @Override
    public UserProfileDTO registerPatient(UserRegistrationDTO userRegistrationDTO) {
        Role role = roleRepository.findById(1).orElse(null);
        return UserRegistrationWithRole(userRegistrationDTO,role);
    }

    @Override
    public UserProfileDTO registerDentist(UserRegistrationDTO userRegistrationDTO) {
        Role role = roleRepository.findById(2).orElse(null);
        return UserRegistrationWithRole(userRegistrationDTO,role);
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())

        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        //Aca se va generar el token

        String token = tokenProvider.createAccessToken(authentication);

        AuthResponseDTO responseDTO = userMapper.toAuthResponseDTO(user,token);
        return responseDTO;
    }
    public Integer getAuthenticatedUserIdFromJWT() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String token = (String) authentication.getCredentials(); // Obtén el token del objeto de autenticación

            // Extraer el email del token
            Claims claims = tokenProvider.getJwtParser().parseClaimsJws(token).getBody();
            String email = claims.getSubject();


            // Buscar el usuario usando el email
            User user = userRepository.findByEmail(email).orElse(null); // Debes implementar este método en tu UserService
            return user != null ? user.getId() : null;
        }
        return null; // Si no hay autenticación, devuelve null
    }
    @Override
    public UserProfileDTO uptadteUserProfile(Integer id, UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(id).orElseThrow( ( )-> new UserNotFoundException("Usuario no encontrado"));

        boolean existPatient = patientRepository.existsByNameAndLastnameAndUserIdNot(userProfileDTO.getName(),userProfileDTO.getLastname(),id);
        boolean existDentist= dentistRepository.existsByNameAndLastnameAndUserIdNot(userProfileDTO.getName(),userProfileDTO.getLastname(),id);

        if (existPatient || existDentist) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre y apellido");
        }

        if(user.getPatient()!=null)
        {
            if(userProfileDTO.getEmail()!=null) user.setEmail(userProfileDTO.getEmail());
            if(userProfileDTO.getName()!=null)user.getPatient().setName(userProfileDTO.getName());
            if(userProfileDTO.getLastname()!=null)user.getPatient().setLastname(userProfileDTO.getLastname());
            if(userProfileDTO.getGender()!=null) user.getPatient().setGender(userProfileDTO.getGender());
            if(userProfileDTO.getBirthday()!=null) user.getPatient().setBirthday(userProfileDTO.getBirthday());
            if(userProfileDTO.getPhone()!=null) user.getPatient().setPhone(userProfileDTO.getPhone());
            EmergencyRequestDTO emergencyInfo = new EmergencyRequestDTO();
            if(userProfileDTO.getParent()!=null) emergencyInfo.setParent(userProfileDTO.getParent());
            if(userProfileDTO.getPname()!=null) emergencyInfo.setName(userProfileDTO.getPname());
            if(userProfileDTO.getPphone()!=null) emergencyInfo.setPhone(userProfileDTO.getPphone());
            if(userProfileDTO.getPdir()!=null) emergencyInfo.setPhone(userProfileDTO.getPdir());
            EmergencyResponseDTO emergencyResponseDTO =emergencyService.updateEmergencyInfo(user.getPatient().getEmergency().getId(),emergencyInfo);
            user.getPatient().setEmergency(emergencyMapper.convertToEntity(emergencyResponseDTO));

        }

        if(user.getDentist()!=null)
        {
            if(userProfileDTO.getEmail()!=null) user.setEmail(userProfileDTO.getEmail());
            if(userProfileDTO.getName()!=null)user.getDentist().setName(userProfileDTO.getName());
            if(userProfileDTO.getLastname()!=null)user.getDentist().setLastname(userProfileDTO.getLastname());
            if(userProfileDTO.getGender()!=null) user.getDentist().setGender(userProfileDTO.getGender());
            if(userProfileDTO.getBirthday()!=null) user.getDentist().setBirthday(userProfileDTO.getBirthday());
            if(userProfileDTO.getPhone()!=null) user.getDentist().setPhone(userProfileDTO.getPhone());
            if(userProfileDTO.getCicle()!=null) user.getDentist().setCicle(userProfileDTO.getCicle());
            if(userProfileDTO.getStudyCenter()!=null) user.getDentist().setStudyCenter(userProfileDTO.getStudyCenter());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(updatedUser);
    }

    @Override
    public UserProfileDTO getUserProfilebyId(Integer id) {
        User user = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("Usuario no encontrado"));
        return userMapper.toUserProfileDTO(user);
    }

    @Override
    public User getUserbyId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(( )-> new UserNotFoundException("Usuario no encontrado"));
        return user;
    }

    private UserProfileDTO UserRegistrationWithRole(UserRegistrationDTO userRegistrationDTO,Role role) {
        boolean existByEmail = userRepository.existsByEmail(userRegistrationDTO.getEmail());
        boolean existDentist = dentistRepository.existsByNameAndLastname(userRegistrationDTO.getName(), userRegistrationDTO.getLastname());
        boolean existPatient = patientRepository.existsByNameAndLastname(userRegistrationDTO.getName(), userRegistrationDTO.getLastname());

        if (existByEmail) {
            throw new IllegalArgumentException("Email ya esta registrado");
        }
        if (existDentist || existPatient) {
            throw new IllegalArgumentException("El usuario ya esta registrado");
        }

        userRegistrationDTO.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        User user = userMapper.toUserEntity(userRegistrationDTO);
        user.setRole(role);

        if (Objects.equals(role.getName(), "PATIENT")) {
            Patient patient = new Patient();
            patient.setName(userRegistrationDTO.getName());
            patient.setLastname(userRegistrationDTO.getLastname());
            patient.setBirthday(userRegistrationDTO.getBirthday());
            patient.setGender(userRegistrationDTO.getGender());
            patient.setPhone(userRegistrationDTO.getPhone());
            patient.setUser(user);
            user.setPatient(patient);
        } else if (Objects.equals(role.getName(), "DENTIST")) {
            Dentist dentist = new Dentist();
            dentist.setName(userRegistrationDTO.getName());
            dentist.setLastname(userRegistrationDTO.getLastname());
            dentist.setBirthday(userRegistrationDTO.getBirthday());
            dentist.setGender(userRegistrationDTO.getGender());
            dentist.setPhone(userRegistrationDTO.getPhone());
            dentist.setCondition(userRegistrationDTO.getCondition());
            dentist.setStudyCenter(userRegistrationDTO.getStudyCenter());
            dentist.setCop(userRegistrationDTO.getCop());
            dentist.setCicle(userRegistrationDTO.getCicle());
            dentist.setUser(user);
            user.setDentist(dentist);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(savedUser);
    }



}
