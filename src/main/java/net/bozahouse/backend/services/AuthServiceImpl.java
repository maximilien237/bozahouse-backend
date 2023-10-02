package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.dtos.AppUserDTO;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.exception.ErrorCodes;
import net.bozahouse.backend.exception.InvalidEntityException;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.validators.AppUserValidator;
import net.bozahouse.backend.payload.request.LoginRequest;
import net.bozahouse.backend.payload.response.JwtResponse;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.security.ControllerVariables;
import net.bozahouse.backend.security.services.JwtService;
import net.bozahouse.backend.services.email.EmailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private AppUserService appUserService;
    private AppUserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private EmailSender sender;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Override
    public JwtResponse authenticate(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser appUser = appUserService.getAppUserByEmail(authentication.getName());

        if (!appUser.isActivated()){
            throw new EntityNotFoundException("user has been disabled !");
        }

        if (!appUser.isFirstConnexion()){
            appUser.setFirstConnexion(true);
            appUser = userRepo.save(appUser);
        }

        if (appUser.isActivated() && appUser.isFirstConnexion()){
            appUser.setCountConnexion(appUser.getCountConnexion() + 1);
            userRepo.save(appUser);

            var jwt = jwtService.generateToken((UserDetails) appUser);

            return new JwtResponse(jwt, appUser.getEmail());
        }

        return null;

    }

    @Override
    public AppUserDTO register(AppUserDTO dto){
        log.info("creating appUser...");

        List<String> errors = AppUserValidator.validate(dto);
        if (!errors.isEmpty() ) {
            log.error("AppUser is not valid {}", dto);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.BAD_REQUEST, errors);
        }

        if ( !Objects.equals(dto.getPassword(), dto.getConfirmPassword())) {
            throw new InvalidEntityException("Vos mot de passe ne correspondent pas ", ErrorCodes.BAD_REQUEST);
        }

        if (appUserService.existsByEmail(dto.getEmail())) {
            throw new InvalidEntityException("Error: Email déjà utilisé!", ErrorCodes.BAD_REQUEST);
        }
        ControllerVariables controllerVariables = new ControllerVariables();

        AppUser appUser = AppUserDTO.mapToEntity(dto);
        appUser.setActivated(false);
        appUser.setFirstConnexion(false);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setConfirmPassword(passwordEncoder.encode(appUser.getConfirmPassword()));

        appUser = userRepo.save(appUser);
        appUserService.addRoleToUser(appUser.getUsername(), controllerVariables.getUser());

        log.info("send mail to user");
        sender.validateAccount(appUser);

        return AppUserDTO.mapToDTO(appUser);
    }


    @Override
    public String checkEmail(String email)  {
        AppUser appUser = appUserService.getAppUserByEmail(email);
        if(appUser != null && appUser.getCountConnexion() == 0){
            appUser.setActivated(true);
            userRepo.save(appUser);
            return "Compte activé avec succès";
        } else {
            throw new EntityNotFoundException("compte désactivé ou inexistant avec cet email !");
        }
    }

}
