package net.bozahouse.backend.repositories;

import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.forms.AppUserForm;
import net.bozahouse.backend.utils.DateUtils;
import net.bozahouse.backend.utils.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
class AppUserRepoTest {

    //@Autowired
    //private AppUserRepo userRepo;



   // @Test
    //@DisplayName("check an existing email")
    /*void existsByEmail() {
        //given
        String email = "none@gmail.com";
        AppUserForm form1 = new AppUserForm();
        form1.setUsername("none");
        form1.setLastname("none");
        form1.setAccount("talent");
        form1.setBirthday(DateUtils.convertDateToString(new Date()));
        form1.setHowKnowUs("facebook");
        form1.setFirstname("surname");
        form1.setPassword("1234");
        form1.setConfirmPassword("1234");
        form1.setReferralCode("none");
        form1.setEmail("none@gmail.com");
        form1.setAcceptTerms(true);
        form1.setSex("Homme");
        AppUser appUser = FormToEntityConverter.convertFormToAppUser(form1);
        appUser.setActivated(true);
        appUser.setCreatedAt(new Date());
        appUser.setFirstConnexion(false);
        appUser.setId(RandomUtils.id());

        userRepo.save(appUser);

        //when
        boolean expected = userRepo.selectExistsEmail(email);

        //then
        assertThat(expected).isTrue();
    }*/

    //@Test
   /* void notExistsByEmail() {
        //given
        String email = "none@gmail.com";

        //when
        boolean expected = userRepo.selectExistsEmail(email);

        //then
        assertThat(expected).isFalse();
    }*/
}