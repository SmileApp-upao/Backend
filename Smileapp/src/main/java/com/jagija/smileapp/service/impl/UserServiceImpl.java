package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmergencyService emergencyService;
    @Autowired
    private EmergencyRepository emergencyRepository;
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
    private ClinicRepository clinicRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private IUploadFileServiceImpl uploadFileService;


    @Override
    public UserProfileDTO registerPatient(UserRegistrationDTO userRegistrationDTO) {

        Role role = roleRepository.findById(1).orElse(null);
        return UserRegistrationWithRole(userRegistrationDTO,role);

    }

    @Override
    public UserProfileDTO registerDentist(UserRegistrationDTO userRegistrationDTO) throws IOException {
        Role role = roleRepository.findById(2).orElse(null);
        if(userRegistrationDTO.getCondition()==null)
        {
            throw new IllegalArgumentException("No debe ser nulo la condicion");
        }
        if(userRegistrationDTO.getCondition().equals("Profesional"))
        {
            if(userRegistrationDTO.getCop()==null){
                throw new IllegalArgumentException("Cop required");
            }
            VallidCopDTO cop = new VallidCopDTO();
            cop.setCop(userRegistrationDTO.getCop());
            if(validCop(cop)){
                return UserRegistrationWithRole(userRegistrationDTO,role);
            }
            else{
                throw new IllegalArgumentException("Dentista no esta habilitado");
            }
        }
        else
        {
            return UserRegistrationWithRole(userRegistrationDTO,role);
        }
    }
    @Override
    public EmergencyResponseDTO createEmergencyInfo(EmergencyRequestDTO emergencyRequestDTO) {
        Integer userId = getAuthenticatedUserIdFromJWT();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        if(user.getPatient().getEmergency()!=null)
        {
            throw new IllegalArgumentException("El usuario ya tiene informacion de emergencia creada");
        }
        Emergency emergency = emergencyMapper.convertToEntity(emergencyRequestDTO);
        emergency.setPatient(user.getPatient());

        emergency = emergencyRepository.save(emergency); // Persistir la entidad Emergency


        user.getPatient().setEmergency(emergency);

        userRepository.save(user);

        return emergencyMapper.convertToDTO(emergency); // Retorna el DTO de Emergency
    }

    public boolean validCop(VallidCopDTO copDTO) throws IOException
    {  String URL = "https://sigacop.cop.org.pe/consultas_web/consulta_colegiado.asp";
        String estado="";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(URL);

            // Configura el cuerpo de la solicitud POST
            String formData = "TxtBusqueda=" + copDTO.getCop() + "&eje=30&id1=&page=1";
            post.setEntity(new StringEntity(formData));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            try (CloseableHttpResponse response = client.execute(post)) {
                String html = EntityUtils.toString(response.getEntity());
                estado = extraerEstado(html);
            }
        }
        return estado.equals("HABILITADO");
    }

    private String extraerEstado(String html) {
        Document document = Jsoup.parse(html);
        Element estadoElement = document.selectFirst("table.lista tr:nth-of-type(2) td:nth-of-type(5)");

        if (estadoElement != null) {
            return estadoElement.text();
        }
        return "No se encontró información para el código COP proporcionado.";
    }

    public Map<String, String> obtenerDatosCop(VallidCopDTO copDTO) throws IOException {
        String URL = "https://sigacop.cop.org.pe/consultas_web/consulta_colegiado.asp";
        Map<String, String> resultado = new HashMap<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(URL);

            String formData = "TxtBusqueda=" + copDTO.getCop() + "&eje=30&id1=&page=1";
            post.setEntity(new StringEntity(formData));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            try (CloseableHttpResponse response = client.execute(post)) {
                String html = EntityUtils.toString(response.getEntity());
                resultado = extraerNombresYRegion(html);
            }
        }
        return resultado;
    }

    private Map<String, String> extraerNombresYRegion(String html) {
        Document document = Jsoup.parse(html);
        Element nombreElement = document.selectFirst("table.lista tr:nth-of-type(2) td:nth-of-type(3)");

        Map<String, String> resultado = new HashMap<>();

        if (nombreElement != null) {
            String[] partes = nombreElement.text().split(" ");
            if (partes.length >= 2) {
                // Las dos primeras palabras son apellidos
                resultado.put("apellidos", partes[0] + " " + partes[1]);

                StringBuilder nombres = new StringBuilder();
                for (int i = 2; i < partes.length; i++) {
                    nombres.append(partes[i]).append(" ");
                }
                resultado.put("nombres", nombres.toString().trim());
            } else {
                resultado.put("apellidos", partes[0]);
                resultado.put("nombres", partes[1]);
            }
        }

        return resultado;
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = userPrincipal.getUser();

            String token = tokenProvider.createAccessToken(authentication);

            return userMapper.toAuthResponseDTO(user, token);

        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Error en las credenciales");
        }
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
        Integer AutenticatedId=getAuthenticatedUserIdFromJWT();
        if(AutenticatedId!=id)
        {
            throw new IllegalArgumentException("No puedes editar un perfil que no es el tuyo");
        }
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
            if(userProfileDTO.getPdir()!=null) emergencyInfo.setDir(userProfileDTO.getPdir());
            EmergencyResponseDTO emergencyResponseDTO =emergencyService.updateEmergencyInfo(user.getPatient().getId(),emergencyInfo);
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
            if(userProfileDTO.getDescription()!=null) user.getDentist().setDescription(userProfileDTO.getDescription());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(updatedUser);
    }

    @Override
    public void updateUserImage(Integer userId, MultipartFile image) {

        Integer AutenticatedId=getAuthenticatedUserIdFromJWT();

        if(AutenticatedId!=userId)
        {
            throw new IllegalArgumentException("No puedes editar un perfil que no es el tuyo");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (image != null && !image.isEmpty()) {
            try {
                String fileName = uploadFileService.copy(image);
                if (user.getPatient() != null) {
                    user.getPatient().setImage(fileName);
                } else if (user.getDentist() != null) {
                    user.getDentist().setImage(fileName);
                }
                userRepository.save(user);
            } catch (IOException e) {
                throw new RuntimeException("Error al cargar la imagen: " + e.getMessage(), e);
            }
        }
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
        boolean existdniPat = patientRepository.existsByDni(userRegistrationDTO.getDni());
        boolean existdniDen = dentistRepository.existsByDni(userRegistrationDTO.getDni());
        if(existdniPat)
        {
            throw new IllegalArgumentException("Ya existe un Paciente registrado con ese dni");
        }
        if(existdniDen)
        {
            throw new IllegalArgumentException("Ya existe un Dentista registrado con ese dni");
        }
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
            patient.setDni(userRegistrationDTO.getDni());
            try {
                if (userRegistrationDTO.getImage() != null && !userRegistrationDTO.getImage().isEmpty()) {
                    String fileName = uploadFileService.copy(userRegistrationDTO.getImage());
                    patient.setImage(fileName);
                } else {
                    patient.setImage(null);
                }
            } catch (IOException e){
                throw new RuntimeException("Error al cargar la imagen: " + e.getMessage(), e);
            }
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
            try {
                if (userRegistrationDTO.getImage() != null && !userRegistrationDTO.getImage().isEmpty()) {
                    String fileName = uploadFileService.copy(userRegistrationDTO.getImage());
                    dentist.setImage(fileName);
                } else {
                    dentist.setImage(null);
                }
            } catch (IOException e){
                throw new RuntimeException("Error al cargar la imagen: " + e.getMessage(), e);
            }
            if(userRegistrationDTO.getCop()!=null)dentist.setCop(userRegistrationDTO.getCop());

            if(userRegistrationDTO.getCicle()!=null)dentist.setCicle(userRegistrationDTO.getCicle());
            dentist.setDni(userRegistrationDTO.getDni());
            dentist.setUser(user);
            user.setDentist(dentist);
        }

        User savedUser = userRepository.save(user);

        if( savedUser.getRole().getName().equals("DENTIST") && userRegistrationDTO.getCondition().equals("Estudiante"))
        {
            Clinic clincaUpao=clinicRepository.findById(1).orElse(null);
            if(clincaUpao==null)
            {
                throw new ResourceNotFoundException("La clinica UPAO no existe");
            }
            List<Dentist> dentistas =clincaUpao.getDentistas();
            dentistas.add(savedUser.getDentist());
            clincaUpao.setDentistas(dentistas);
            clinicRepository.save(clincaUpao);
        }

        return userMapper.toUserProfileDTO(savedUser);
    }
}
