package net.bozahouse.backend.validators;


import java.util.ArrayList;
import java.util.List;


import net.bozahouse.backend.dtos.AppUserDTO;
import org.springframework.util.StringUtils;

public class AppUserValidator {

    private AppUserValidator(){}

    public static List<String> validate(AppUserDTO dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner l'email ");
            errors.add("Veuillez renseigner le password ");
            errors.add("Veuillez renseigner le confirmPassword");
            errors.add("Veuillez renseigner le lastname'");
            errors.add("Veuillez renseigner le firstname'");
            errors.add("Veuillez renseigner le account'");
            errors.add("Veuillez renseigner le sex'");
            errors.add("Veuillez renseigner le acceptTerms'");
            errors.add("Veuillez renseigner le howKnowUs'");
            errors.add("Veuillez renseigner le birthday'");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("Veuillez renseigner l' email");
        }
        if (!StringUtils.hasLength(dto.getPassword())) {
            errors.add("Veuillez renseigner le password ");
        }
        if (!StringUtils.hasLength(dto.getConfirmPassword())) {
            errors.add("Veuillez renseigner le confirmPassword ");
        }
        if (!StringUtils.hasLength(dto.getLastname())) {
            errors.add("Veuillez renseigner le lastname");
        }
        if (!StringUtils.hasLength(dto.getFirstname())) {
            errors.add("Veuillez renseigner le firstname'");
        }
        if (!StringUtils.hasLength(dto.getAccount())) {
            errors.add("Veuillez renseigner le account'");
        }
        if (!StringUtils.hasLength(dto.getSex())) {
            errors.add("Veuillez renseigner le sex'");
        }
        if (!dto.isAcceptTerms()) {
            errors.add("Veuillez renseigner le acceptTerms'");
        }
        if (!StringUtils.hasLength(dto.getHowKnowUs())) {
            errors.add("Veuillez renseigner le howKnowUs'");
        }
        if (dto.getBirthday() == null) {
            errors.add("Veuillez renseigner le birthday'");
        }

        return errors;
    }

}
