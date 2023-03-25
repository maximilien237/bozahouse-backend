package net.bozahouse.backend.security.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.RoleNotFoundException;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.AppUserDates;
import net.bozahouse.backend.model.entities.Subscription;
import net.bozahouse.backend.repositories.AppRoleRepo;
import net.bozahouse.backend.repositories.AppUserDatesRepo;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.SubscriptionRepo;
import net.bozahouse.backend.security.ControllerVariables;
import net.bozahouse.backend.services.AppUserService;
import net.bozahouse.backend.services.email.EmailSender;
import net.bozahouse.backend.utils.Constant;
import net.bozahouse.backend.utils.DateUtils;
import net.bozahouse.backend.utils.RandomUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
  private AppUserRepo userRepo;

  private AppRoleRepo roleRepo;
  private SubscriptionRepo subscriptionRepo;

  EmailSender sender;
  private AppUserService appUserService;

  private AppUserDatesRepo userDatesRepo;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  public void saveAppUser(AppUser appUser) {
/*    AppRole role = new AppRole();
    role.setName("USER");
    roleRepo.save(role);
  */


    appUser.setId(RandomUtils.id());
    appUser.setActivated(false);
    appUser.setFirstConnexion(false);
    appUser.setCreatedAt(DateUtils.currentDate());
    appUser.setPromoCode(appUser.getUsername() + RandomUtils.generate2Int());
    userRepo.save(appUser);
    try {
      appUserService.addRoleToUser(appUser.getUsername(), ControllerVariables.USER_ROLE_NAME);

    }catch (AppUserNotFoundException a){
      log.error("user not found" + a.getMessage());
    }catch (RoleNotFoundException r){
      log.error("role not found" + r.getMessage());
    }

  }

  public void saveAdmin(AppUser appUser) {
    AppRole role = new AppRole();
    role.setName("USER");
    AppRole role1 = new AppRole();
    role1.setName("EDITOR");
    AppRole role2 = new AppRole();
    role2.setName("ADMIN");

    roleRepo.save(role);
    roleRepo.save(role1);
    roleRepo.save(role2);


    appUser.setId(RandomUtils.id());
    appUser.setActivated(false);
    appUser.setFirstConnexion(false);
    appUser.setCreatedAt(DateUtils.currentDate());
    appUser.setPromoCode(appUser.getUsername() + RandomUtils.generate2Int());
    userRepo.save(appUser);
    try {
      appUserService.addRoleToUser(appUser.getUsername(), ControllerVariables.USER_ROLE_NAME);
      appUserService.addRoleToUser(appUser.getUsername(), ControllerVariables.EDITOR_ROLE_NAME);
      appUserService.addRoleToUser(appUser.getUsername(), ControllerVariables.ADMIN_ROLE_NAME);
    }catch (AppUserNotFoundException a){
      log.error("user not found" + a.getMessage());
    }catch (RoleNotFoundException r){
      log.error("role not found" + r.getMessage());
    }

  }


  public void currentSaveAppUser(AppUser appUser) {
    userRepo.save(appUser);
  }

  public void saveAppUserConnexionDates(AppUser appUser){
    AppUserDates userDates = new AppUserDates();
    userDates.setLastConnexion(DateUtils.currentDate());
    userDates.setUser(appUser);
    userDatesRepo.save(userDates);

  }

  public void freeSubscription(AppUser appUser){
    AppUser appUser1 = userRepo.findByUsername("admin").orElse(null);
    log.info("creating Subscription...");

    Subscription normalSubscription = new Subscription();
    normalSubscription.setType(Constant.normal);
    normalSubscription.setPeriod(Constant.annuallySubscription);

    Subscription hostSubscription = new Subscription();
    hostSubscription.setType(Constant.host);
    hostSubscription.setPeriod(Constant.annuallySubscription);


    switch (appUser.getAccount()) {
      case Constant.enterprise:

                hostSubscription.setAmount(11500);
                hostSubscription.setEndSubscription(DateUtils.currentDatePlus1Year());

                normalSubscription.setAmount(23000);
                normalSubscription.setEndSubscription(DateUtils.currentDatePlus1Year());

        break;

      case Constant.employer:

                hostSubscription.setAmount(5000);
                hostSubscription.setEndSubscription(DateUtils.currentDatePlus1Year());

                normalSubscription.setAmount(17000);
                normalSubscription.setEndSubscription(DateUtils.currentDatePlus1Year());

        break;

      case Constant.talent:

                hostSubscription.setAmount(4000);
                hostSubscription.setEndSubscription(DateUtils.currentDatePlus1Year());

                normalSubscription.setAmount(11500);
                normalSubscription.setEndSubscription(DateUtils.currentDatePlus1Year());

        break;
    }

    hostSubscription.setInitAmount(hostSubscription.getAmount());
    hostSubscription.setCreatedAt(DateUtils.currentDate());
    hostSubscription.setInitDuration(DateUtils.diff_in_days(DateUtils.currentDate(),hostSubscription.getEndSubscription()));
    hostSubscription.setDuration(hostSubscription.getInitDuration());
    hostSubscription.setDailyAmount(hostSubscription.getInitAmount()/ hostSubscription.getInitDuration());
    hostSubscription.setActivated(true);
    hostSubscription.setBeneficiary(appUser);
    hostSubscription.setInitiator(appUser1);

    //Subscription hostSubscriptionSave =
            subscriptionRepo.save(hostSubscription);
    //sender.notifyRootAboutSubscription(hostSubscriptionSave);
    //sender.notifyAppUserAboutSubscription(hostSubscriptionSave);

      appUser.setActivatedHostSubscription(true);
      appUser.setHostSubscriptionCounter(appUser.getHostSubscriptionCounter() + 1);
      userRepo.save(appUser);


    normalSubscription.setInitAmount(normalSubscription.getAmount());
    normalSubscription.setCreatedAt(DateUtils.currentDate());
    normalSubscription.setInitDuration(DateUtils.diff_in_days(DateUtils.currentDate(),normalSubscription.getEndSubscription()));
    normalSubscription.setDuration(normalSubscription.getInitDuration());
    normalSubscription.setDailyAmount(normalSubscription.getInitAmount()/ normalSubscription.getInitDuration());
    normalSubscription.setActivated(true);
    normalSubscription.setBeneficiary(appUser);
    normalSubscription.setInitiator(appUser1);

    //Subscription normalSubscriptionSave =
            subscriptionRepo.save(normalSubscription);
    //sender.notifyRootAboutSubscription(normalSubscriptionSave);
    //sender.notifyAppUserAboutSubscription(normalSubscriptionSave);


    appUser.setActivatedNormalSubscription(true);
      appUser.setNormalSubscriptionCounter(appUser.getNormalSubscriptionCounter() + 1);
      userRepo.save(appUser);

    System.out.println("end free subscription...");
  }

}


