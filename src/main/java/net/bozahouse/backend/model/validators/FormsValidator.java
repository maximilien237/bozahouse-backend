package net.bozahouse.backend.model.validators;


import net.bozahouse.backend.exception.form.*;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.forms.*;
import net.bozahouse.backend.utils.RegexUtils;

public class FormsValidator {

    //test ok
    public static Boolean validateAppUserForm(AppUserForm form) throws AppUserFormException {

        if (!RegexUtils.validatePassword(form.getPassword()) || !RegexUtils.validatePassword(form.getConfirmPassword())
                || !RegexUtils.validateLastname(form.getLastname()) || !RegexUtils.validateFirstname(form.getFirstname())
                || !RegexUtils.validateUsername(form.getUsername()) || !RegexUtils.validateEmail(form.getEmail())
                || !form.getReferralCode().isEmpty() && !RegexUtils.validateReferralCode(form.getReferralCode())){
            throw new AppUserFormException("regex error");
        }

        if (form.getEmail().isEmpty() || form.getLastname().isEmpty() || form.getPassword().isEmpty()
                || form.getConfirmPassword().isEmpty() || form.getUsername().isEmpty() || form.getBirthday()==null || form.getHowKnowUs().isEmpty()
                || form.getFirstname().isEmpty() || form.getAccount().isEmpty() || form.getSex().isEmpty()){
            throw new AppUserFormException("vous avez au moins un champs vie");
        }

        if (!form.getPassword().equals(form.getConfirmPassword())){
            throw new AppUserFormException("password does not match");
        }

        return true;
    }



    public static Boolean validateSubscriptionForm(SubscriptionForm form) throws SubscriptionFormException {
        if (form.getPeriod().isEmpty()  || form.getType().isEmpty()){
            throw new SubscriptionFormException("you have at least empty field on your payment form");
        }
        return true;
    }

    public static Boolean validateOfferForm(OfferForm form) throws OfferFormException {

       if (!RegexUtils.validateUrl(form.getFcb()) || !form.getFcb().contains("https://www.facebook.com/")
               || !RegexUtils.validateUrl(form.getWeb())) {
           throw new OfferFormException("fcb url or web url is not valid");
       }

       if (!RegexUtils.validateText(form.getMission()) || !RegexUtils.validateAddress(form.getAddress())
               ||!RegexUtils.validateEmail(form.getEmail()) || !RegexUtils.validateText(form.getProfile())
       || !RegexUtils.validateNumber(String.valueOf(form.getNeedPeople())) || !RegexUtils.validateText(form.getSkills())
       || !RegexUtils.validateNumber(form.getWhatsAppNumber()) || !RegexUtils.validateNumber(form.getTel())
       || !RegexUtils.validateText(form.getDomain()) || !RegexUtils.validateText(form.getTitle())
       || !RegexUtils.validateText(form.getName()) || !RegexUtils.validateNumber(form.getSalary())){
           throw new OfferFormException("pattern error");
       }

        if (form.getFcb().isEmpty() || form.getWhatsAppNumber().isEmpty() || form.getWeb().isEmpty()
                || form.getEndOffer()==null || form.getAddress().isEmpty() || form.getContract().isEmpty()
                || form.getDomain().isEmpty() || form.getEmail().isEmpty() || form.getExperience().isEmpty()
                || form.getMission().isEmpty() || form.getName().isEmpty() || form.getNeedPeople()==0
                || form.getSalary().isEmpty() || form.getProfile().isEmpty() || form.getType().isEmpty()
        || form.getTel().isEmpty() || form.getTitle().isEmpty() || form.getWorkMode().isEmpty()
                || form.getSkills().isEmpty() ){
            throw new OfferFormException("vous avez au moins un champs vie");
        }

        if (!form.getTel().startsWith("6") || !form.getTel().startsWith("2")
                || !form.getWhatsAppNumber().startsWith("6") || !form.getWhatsAppNumber().startsWith("2")){
            throw new OfferFormException("ce numéro n'est pas camerounais !");
        }
        return true;
    }

    public static Boolean validateTalentForm(TalentForm form) throws TalentFormException {

        System.out.println("bool 1");
        if (!RegexUtils.validateText(form.getTitle()) || !RegexUtils.validateText(form.getDomain())
                || !RegexUtils.validateText(form.getSkills()) || !form.getSalary().isEmpty() && !RegexUtils.validateNumber(form.getSalary())
                || !RegexUtils.validateNumber(form.getTel()) || !form.getWhatsAppNumber().isEmpty() && !RegexUtils.validateNumber(form.getWhatsAppNumber())
                || !RegexUtils.validateAddress(form.getAddress()) || !RegexUtils.validateEmail(form.getEmail())
                || !form.getLinkedin().isEmpty() && !RegexUtils.validateUrl(form.getLinkedin()) && !form.getLinkedin().contains("https://www.linkedin.com/")
                || !form.getGithub().isEmpty() && !RegexUtils.validateUrl(form.getGithub()) && !form.getGithub().contains("https://www.github.com/") ){
            throw new TalentFormException("pattern error");
        }

        System.out.println("bool 2");
        if(form.getType().isEmpty() ||  form.getLevel().isEmpty() ||  form.getCountryCode().isEmpty()
                ||  form.getTel().isEmpty() || form.getTitle().isEmpty() || form.getDomain().isEmpty()
                || form.getSkills().isEmpty() || form.getWorkMode().isEmpty() || form.getExperience().isEmpty()
                || form.getAddress().isEmpty() || form.getContract().isEmpty() || form.getSalaryChoice().isEmpty() ){
            throw new TalentFormException("you have at least one empty field");
        }
        System.out.println("bool 3");
        return true;
    }

    //test ok
    public static Boolean validateLoginForm(LoginForm form) throws LoginFormException {

        //if (form.getUsername().matches(""))
        if (form.getUsername().isEmpty() || form.getPassword().isEmpty()){
            throw new LoginFormException("your login form is empty");
        }

        if (!RegexUtils.validateUsername(form.getUsername()) || !RegexUtils.validatePassword(form.getPassword())){
            throw new LoginFormException("pattern error");
        }

        return true;
    }


    public static Boolean validateAppRoleForm(AppRole form) throws ValidationFormException {
        if (form.getName().isEmpty()){
            throw new ValidationFormException("empty role form");
        }
        return true;
    }

    public static Boolean validateNewsForm(NewsletterForm form) throws NewsFormException {

        if (!RegexUtils.validateText(form.getEnglishContent()) || !RegexUtils.validateText(form.getFrenchContent())
        || !RegexUtils.validateText(form.getSubject())){
            throw new NewsFormException("pattern error");
        }

        if (form.getEnglishContent().isEmpty() || form.getFrenchContent().isEmpty()
                || form.getSubject().isEmpty() || form.getSendingDate()==null){
            throw new NewsFormException("error validation form");
        }
        return true;
    }


}
