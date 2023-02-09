package net.bozahouse.backend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

   String regex = "((http|https)://)(www.)? + [a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z] + {2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)";

    //string pattern
     static  String addressPattern = "[A-Z][a-z-0-9-çèéàê'-]+,[A-Z][a-z-çèéà]+,[A-Z][a-z-çèéà]{4,30}";//test OK
     static  String referralCodePattern = "[a-z0-9]$";
     static  String lastnamePattern = "^(?=.*[A-Z' -]).{3,30}+";
     static  String firstnamePattern = "^(?=.*[A-Z][a-z' -]).{3,30}+";
     static  String usernamePattern = "^(?=.*[a-z]).{3,12}$";
     static  String numberPattern = "[26][0-9]";//test OK
     static  String textPattern = "[A-Z][a-z-çèéàê()+:!',. ]+";
     static  String emailPattern = "^[a-z0-9_+&*-]+(?:\\.[a-z0-9_+&*-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,15}$";//test OK
     static  String urlPattern = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9a-f]{2}|[-()_.!~*';/?:@&=+$,a-z0-9])+)([).!';/?:,][[:blank:|:blank:]])?$";//test OK
     static  String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
     static  String phoneNumberPattern = "[0-9]{3}-[0-9]{2}-[0-9]{2}-[0-9]{2}";


    //pattern
     static Pattern VALID_ADDRESS_REGEX = Pattern.compile("^(?=.*[A-Z][a-z-0-9-çèéàê'-]+)*,(?=.*[A-Z][a-z-çèéà]+)*,(?=.*[A-Z][a-z-çèôé '-]).{4,30}$");
     static Pattern VALID_REFERRAL_CODE_REGEX = Pattern.compile("^(?=.*[a-z0-9])$");
     static  Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^([a-z0-9_+&*-]+)(?:\\.[a-z0-9_+&*-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,15}$");//ok
     static  Pattern VALID_URL_REGEX = Pattern.compile("^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9a-f]{2}|[-()_.!~*';/?:@&=+$,a-z0-9])+)([).!';/?:,][[:blank:|:blank:]])?$");
     static  Pattern VALID_SAFE_TEXT_REGEX = Pattern.compile("^(?=.*[A-Z][a-z-çèéàê()+:!',. ])");
     static  Pattern VALID_NUMBER_REGEX = Pattern.compile("^(?=.*\\d)");
     static  Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");//ok
     static  Pattern VALID_USERNAME_REGEX = Pattern.compile("^(?=.*[a-z]).{3,12}$");//ok
     static  Pattern VALID_LASTNAME_REGEX = Pattern.compile("^(?=.*[A-Z' -]).{3,30}+");//ok
     static  Pattern VALID_FIRSTNAME_REGEX = Pattern.compile("^(?=.*[A-Z][a-z' -]).{3,30}+");//ok


    public static boolean validateAddress(String address){
        Matcher matcher = VALID_ADDRESS_REGEX.matcher(address);
        return matcher.find();
    }
    public static boolean validateReferralCode(String referralCode){
        Matcher matcher = VALID_REFERRAL_CODE_REGEX.matcher(referralCode);
        return matcher.find();
    }

    public static boolean validateEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validateUrl(String url){
        Matcher matcher = VALID_URL_REGEX.matcher(url);
        return matcher.find();
    }

    public static boolean validateText(String text){
        Matcher matcher = VALID_SAFE_TEXT_REGEX.matcher(text);
        return matcher.find();
    }

    public static boolean validateNumber(String number){
        Matcher matcher = VALID_NUMBER_REGEX.matcher(number);
        return matcher.find();
    }

    public static boolean validatePassword(String password){
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    public static boolean validateUsername(String username){
        Matcher matcher = VALID_USERNAME_REGEX.matcher(username);
        return matcher.find();
    }

    public static boolean validateLastname(String lastname){
        Matcher matcher = VALID_LASTNAME_REGEX.matcher(lastname);
        return matcher.find();
    }

    public static boolean validateFirstname(String firstname){
        Matcher matcher = VALID_FIRSTNAME_REGEX.matcher(firstname);
        return matcher.find();
    }


    /*public static boolean validateAddress(String address){
        return address.matches(addressPattern);
    }
    public static boolean validateReferralCode(String referralCode){
        return referralCode.matches(referralCodePattern);
    }

    public static boolean validateEmail(String email){
        return email.matches(emailPattern);
    }

    public static boolean validateUrl(String url){
        return url.matches(urlPattern);
    }

    public static boolean validateText(String text){
        return text.matches(textPattern);
    }

    public static boolean validateNumber(String number){
        return number.matches(numberPattern);
    }

    public static boolean validatePassword(String password){
        return password.matches(passwordPattern);
    }

    public static boolean validateUsername(String username){
        return username.matches(usernamePattern);
    }

    public static boolean validateLastname(String lastname){
        return lastname.matches(lastnamePattern);
    }

    public static boolean validateFirstname(String firstname){
        return firstname.matches(firstnamePattern);
    }*/
}
