package net.bozahouse.backend.utils;

import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.repositories.AppRoleRepo;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.security.ControllerVariables;
import net.bozahouse.backend.services.AppUserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Slf4j
public class LoadInitialData {

    private LoadInitialData() {}

    public static void loadAdminData(AppUserService appUserService, AppUserRepo appUserRepo, AppRoleRepo roleRepo, PasswordEncoder encoder) {
    log.info("loading admin data...");
        AppRole role = new AppRole();
        role.setName("USER");
        AppRole role1 = new AppRole();
        role1.setName("EDITOR");
        AppRole role2 = new AppRole();
        role2.setName("ADMIN");

        roleRepo.save(role);
        roleRepo.save(role1);
        roleRepo.save(role2);

        AppUser appUser = AppUser.builder()
                .acceptTerms(true)
                .account("enterprise")
                .birthday(new Date())
                .password(encoder.encode("1234"))
                .confirmPassword(encoder.encode("1234"))
                .countConnexion(0)
                .email("maximiliendenver@gmail.com")
                .firstConnexion(true)
                .firstname("KENGNE KONGNE")
                .lastname("Maximilien")
                .howKnowUs("WhatsApp")
                .activated(true)
                .sex("M")
                .build();

        appUser = appUserRepo.save(appUser);

        ControllerVariables controllerVariables = new ControllerVariables();
        appUserService.addRoleToUser(appUser.getEmail(), controllerVariables.getAdmin());
        appUserService.addRoleToUser(appUser.getEmail(), controllerVariables.getEditor());
        appUserService.addRoleToUser(appUser.getEmail(), controllerVariables.getUser());

    }



}
