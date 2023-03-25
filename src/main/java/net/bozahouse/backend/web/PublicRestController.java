package net.bozahouse.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.form.AppUserFormException;
import net.bozahouse.backend.exception.form.LoginFormException;
import net.bozahouse.backend.exception.other.ResourceDisableException;
import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.forms.AppUserForm;
import net.bozahouse.backend.model.forms.LoginForm;
import net.bozahouse.backend.payload.response.JwtResponse;
import net.bozahouse.backend.payload.response.MessageResponse;
import net.bozahouse.backend.security.jwt.JwtUtils;
import net.bozahouse.backend.security.services.UserDetailsImpl;
import net.bozahouse.backend.security.services.UserDetailsServiceImpl;
import net.bozahouse.backend.services.AppUserService;
import net.bozahouse.backend.services.email.EmailSender;
import net.bozahouse.backend.utils.DateUtils;
import net.bozahouse.backend.utils.RandomUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api/auth/v1/")
public class PublicRestController {

    AuthenticationManager authenticationManager;
    AppUserService appUserService;
    EmailSender sender;
    PasswordEncoder encoder;
    UserDetailsServiceImpl userDetailsService;
    JwtUtils jwtUtils;


    @PostMapping("signIn")
    public ResponseEntity<?> authenticate(@RequestBody LoginForm form) throws AppUserNotFoundException, LoginFormException {

      /*  if (!FormsValidator.validateLoginForm(form)){
            throw new LoginFormException("fill out the form correctly");
        }*/

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final AppUser appUser = appUserService.getAppUserByUsername(authentication.getName());

        try {
            if (!appUser.isActivated()){
                throw new ResourceDisableException("user has been disabled !");
            }
        }catch (ResourceDisableException r){
            log.error(r.getMessage());
        }



        if (appUser.isActivated() && !appUser.isFirstConnexion()){
            boolean isUser = false;

            for (AppRole role : appUser.getRoles()){
                if (role.getName().equalsIgnoreCase("USER")) {
                    isUser = true;
                    break;
                }
            }
            
            if (isUser){
                appUser.setFirstConnexion(true);
                userDetailsService.currentSaveAppUser(appUser);
                userDetailsService.freeSubscription(appUser);
            }

        }

        if (appUser.isActivated() && appUser.isFirstConnexion()){
            appUser.setCountConnexion(appUser.getCountConnexion() + 1);
            userDetailsService.currentSaveAppUser(appUser);
            userDetailsService.saveAppUserConnexionDates(appUser);
            //here
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        }

        return null;

    }


    //test ok
    @PostMapping("signUp")
    public ResponseEntity<?> registration(@RequestBody AppUserForm form) throws AppUserFormException, ParseException {
        System.out.println("pass well 1");
     /*   if (!FormsValidator.validateAppUserForm(form)){
            throw new AppUserFormException("fail of Validation app user form ");
        }*/
        System.out.println("pass well");

        AppUser appUser = FormToEntityConverter.convertFormToAppUser(form);
        appUser.setBirthday(DateUtils.convertStringToDate(form.getBirthday()));

        appUser.setPassword(encoder.encode(appUser.getPassword()));
        appUser.setConfirmPassword(encoder.encode(appUser.getConfirmPassword()));

        if (appUserService.existsByUsername(appUser.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (appUserService.existsByEmail(appUser.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if (!appUserService.existsByPromoCode(appUser.getReferralCode())) {
            appUser.setReferralCode("none");
        }

        if(appUser.getUsername().equalsIgnoreCase("admin")){
            userDetailsService.saveAdmin(appUser);

        }else {

            userDetailsService.saveAppUser(appUser);

        }
        log.info("send mail to user");
        sender.validateAccount(EntityToViewConverter.convertEntityToAppUserView(appUser));
        return ResponseEntity.ok(new MessageResponse("UserAccount registered successfully!"));


    }

//not testing
    @PutMapping("changePassword/{password}")
    public String changePassword(@PathVariable String password) throws AppUserNotFoundException {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = appUserService.getAppUserByUsername(auth.getName());
        AppUser appUser1 = appUserService.getAppUser(appUser.getId());
        appUser1.setPassword(encoder.encode(password));
        userDetailsService.currentSaveAppUser(appUser1);
        return "Password changed";

    }

    @GetMapping("forgotPassword/{username}")
    public String forgotPassword(@PathVariable String username) throws AppUserNotFoundException {
        log.info("passing on reset password function...");
        AppUser appUser = appUserService.getAppUserByUsername(username);
        if (appUser != null && appUser.isActivated() && appUser.isFirstConnexion()){
            appUser.setPassword(encoder.encode(RandomUtils.unique().substring(0,6)));

            userDetailsService.currentSaveAppUser(appUser);
            sender.resetPassword(appUser);

            return "PassWord reset successfully! check your mailbox";
        }else {
            throw new AppUserNotFoundException("user does not exist");
        }

    }

    @GetMapping("checkEmail/{email}")
    public String checkEmail(@PathVariable String email) throws AppUserNotFoundException {
        AppUser appUser = appUserService.getAppUserByEmail(email);
        if(appUser != null && appUser.getCountConnexion() == 0){
            appUser.setActivated(true);
            userDetailsService.currentSaveAppUser(appUser);
            return "Compte activé avec succès";
        } else {
            throw new AppUserNotFoundException("compte désactivé ou inexistant avec cet email !");
        }
    }
}
