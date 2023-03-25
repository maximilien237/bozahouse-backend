package net.bozahouse.backend.services.email;

import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Newsletter;
import net.bozahouse.backend.model.entities.Subscription;
import net.bozahouse.backend.model.entities.Testimony;
import net.bozahouse.backend.model.views.*;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.NewsletterRepo;
import net.bozahouse.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;



@Service
//@Slf4j
public class EmailSenderImpl implements EmailSender {

    private final TemplateEngine templateEngine;
    private final AppUserRepo userRepo;
    private final NewsletterRepo newsletterRepo;

    private final JavaMailSender sender;
    public EmailSenderImpl(TemplateEngine templateEngine, AppUserRepo userRepo, NewsletterRepo newsletterRepo, JavaMailSender sender) {
        this.templateEngine = templateEngine;
        this.userRepo = userRepo;
        this.newsletterRepo = newsletterRepo;
        this.sender = sender;
    }


    @Value("${spring.mail.username}")
    private String sendFrom;

    @Value("${email_admin}")
    private String emailAdmin;

    @Value("${url_backend}")
    private String urlBackend;

    @Override
    public void notifyAppUserAboutSubscription(Subscription subscription){

            SubscriptionView view = EntityToViewConverter.convertEntityToSubscriptionView(subscription);

            long duration = DateUtils.diff_in_days(new Date(), view.getEndSubscription());


            Context context = new Context();
            context.setVariable("username", view.getBeneficiaryUsername());
            context.setVariable("createdAt",DateUtils.dateTimeFormatter(0));
            context.setVariable("initAmount",view.getInitAmount());
            context.setVariable("dailyAmount",view.getDailyAmount());
            context.setVariable("duration",view.getInitDuration());
            context.setVariable("type",view.getType());
            context.setVariable("period", view.getPeriod());
            context.setVariable("endSubscription",DateUtils.dateTimeFormatter(duration));

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse Subscription"));
                messageHelper.setTo(view.getBeneficiaryEmail());

                String content;
                messageHelper.setSubject("Abonnement Réussi !");
                content = templateEngine.process("subscriptionUser", context);

                messageHelper.setText(content,true);
            };

            try {
                sender.send(messagePreparator);
            } catch (MailException e) {
                e.printStackTrace();

            }

    }

    @Override
    public void notifyRootAboutSubscription(Subscription subscription){

            SubscriptionView view = EntityToViewConverter.convertEntityToSubscriptionView(subscription);

            long duration = DateUtils.diff_in_days(new Date(), view.getEndSubscription());

                    Context context = new Context();
                    context.setVariable("username", view.getBeneficiaryUsername());
                    context.setVariable("frenchContent","nouvel abonnement ajouté");
                    context.setVariable("englishContent","new subscription added");
                    context.setVariable("createdAt",DateUtils.dateTimeFormatter(0));
                    context.setVariable("initAmount",view.getInitAmount());
                    context.setVariable("dailyAmount",view.getDailyAmount());
                    context.setVariable("duration",view.getInitDuration());
                    context.setVariable("type",view.getType());
                    context.setVariable("period", view.getPeriod());
                    context.setVariable("endSubscription",DateUtils.dateTimeFormatter(duration));

                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse Subcription"));
                        messageHelper.setTo(emailAdmin);

                        String content;
                        messageHelper.setSubject("1 nouvel abonnement !");
                        content = templateEngine.process("subscription", context);

                        messageHelper.setText(content,true);
                    };

                    try {
                        sender.send(messagePreparator);
                    } catch (MailException e) {
                        e.printStackTrace();

                    }

    }
    @Override
    public void newsletters(Newsletter newsletter) {

                List<AppUser> appUsers = userRepo.listAppUserNotPageable();
                List<AppUserView> appUserViews = appUsers.stream().map(EntityToViewConverter::convertEntityToAppUserView).collect(Collectors.toList());
                //context template message

                for (AppUserView appUserView : appUserViews){

                    Context context = new Context();
                    context.setVariable("username", appUserView.getUsername());
                    context.setVariable("frenchContent", newsletter.getFrenchContent());
                    context.setVariable("englishContent", newsletter.getEnglishContent());
                    context.setVariable("date", DateUtils.dateTimeFormatter(0));

                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse news"));
                        messageHelper.setTo(appUserView.getEmail());

                        String content;
                        messageHelper.setSubject(newsletter.getSubject());
                        content = templateEngine.process("news", context);

                        messageHelper.setText(content,true);
                    };

                    try {
                        sender.send(messagePreparator);
                    } catch (MailException e) {
                        e.printStackTrace();

                    }
                }

    }

    @Override
    public void newslettersPlan() {
       
            List<AppUser> appUsers = userRepo.listAppUserNotPageable();
            List<AppUserView> appUserViews = appUsers.stream().map(EntityToViewConverter::convertEntityToAppUserView).collect(Collectors.toList());

            List<Newsletter> newsletters = newsletterRepo.listNewsNotPageable();
            List<NewsletterView> newsletterViews = newsletters.stream().map(EntityToViewConverter::convertEntityToNewsView).collect(Collectors.toList());

            for (NewsletterView view : newsletterViews){
              if (view.getSendingDate() != null && DateUtils.diff_in_days(new Date(), view.getSendingDate()) == 0){
                      //context template message

                      for (AppUserView appUserView : appUserViews){

                          Context context = new Context();
                          context.setVariable("username", appUserView.getUsername());
                          context.setVariable("frenchContent",view.getFrenchContent());
                          context.setVariable("englishContent",view.getEnglishContent());
                          context.setVariable("date", DateUtils.dateTimeFormatter(0));

                          MimeMessagePreparator messagePreparator = mimeMessage -> {
                              MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                              messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse news"));
                              messageHelper.setTo(appUserView.getEmail());

                              String content;
                              messageHelper.setSubject(view.getSubject());
                              content = templateEngine.process("news", context);

                              messageHelper.setText(content,true);
                          };

                          try {
                              sender.send(messagePreparator);
                          } catch (MailException e) {
                              e.printStackTrace();

                          }
                      }

              }

            }


    }


    @Override
    public void validateAccount(AppUserView appUserView) {
        String url = urlBackend + "/api/auth/v1/checkEmail/";
                //context template message

                    Context context = new Context();
                    context.setVariable("username", appUserView.getUsername());
                    context.setVariable("frenchContent","Bienvenue sur Bozahouse, la maison des travailleurs. pour activer votre compte, cliquez sur le lien ci-dessous !");
                    context.setVariable("validate1",url + appUserView.getEmail());
                    context.setVariable("englishContent","welcome to Bozahouse, house of workers. for activate your account, click on the link down !");
                    context.setVariable("validate2",url + appUserView.getEmail());

                    context.setVariable("date", DateUtils.dateTimeFormatter(0));

                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse"));
                        messageHelper.setTo(appUserView.getEmail());

                        String content;
                        messageHelper.setSubject("Account Validation !");
                        content = templateEngine.process("validateAccount", context);

                        messageHelper.setText(content,true);
                    };

                    try {
                        sender.send(messagePreparator);
                    } catch (MailException e) {
                        e.printStackTrace();

                    }

    }



    @Override
    public void jobNotification(OfferView offerView) {

            List<AppUser> appUsers = userRepo.listAppUserTalent("talent");
            List<AppUserView> appUserViews = appUsers.stream().map(appUser -> EntityToViewConverter.convertEntityToAppUserView(appUser)).collect(Collectors.toList());


            String url = "https://www.bozahouse.com/";
                //context template message

                for (AppUserView appUserView : appUserViews){

                        Context context = new Context();
                        context.setVariable("username", appUserView.getUsername());
                        context.setVariable("frenchContent", "Une offre d'emploi vient d'être ajouté.");
                        context.setVariable("see1", url);
                        context.setVariable("englishContent", "A offer come to be added.");
                        context.setVariable("see2", url);
                        context.setVariable("date", DateUtils.dateTimeFormatter(0));

                        MimeMessagePreparator messagePreparator = mimeMessage -> {
                            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                            messageHelper.setFrom(new InternetAddress(sendFrom, "Alertes Bozahouse Jobs"));
                            messageHelper.setTo(appUserView.getEmail());

                            String content;
                            messageHelper.setSubject("une offre d'emploi pour " + offerView.getTitle());
                            content = templateEngine.process("newJob", context);

                            messageHelper.setText(content, true);
                        };

                        try {
                            sender.send(messagePreparator);
                        } catch (MailException e) {
                            e.printStackTrace();

                        }
                }


    }

    @Override
    public void talentNotification(TalentView talentView) {

            List<AppUser> appUsers = userRepo.listAppUserNotPageable();
            List<AppUserView> appUserViews = appUsers.stream().map(appUser -> EntityToViewConverter.convertEntityToAppUserView(appUser)).collect(Collectors.toList());


            String url = "https://www.bozahouse.com/";
            //context template message

            for (AppUserView appUserView : appUserViews){
                if (appUserView.getAccount().equalsIgnoreCase("employer") || appUserView.getAccount().equalsIgnoreCase("enterprise")){
                    Context context = new Context();
                    context.setVariable("username", appUserView.getUsername());
                    context.setVariable("frenchContent","Un talent vient d'être ajouté.");
                    context.setVariable("see1",url);
                    context.setVariable("englishContent","A talent come to be added.");
                    context.setVariable("see2",url);
                    context.setVariable("date", DateUtils.dateTimeFormatter(0));

                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setFrom(new InternetAddress(sendFrom, "Alertes Bozahouse Talents"));
                        messageHelper.setTo(appUserView.getEmail());

                        String content;
                        messageHelper.setSubject("1 talent pour " + talentView.getTitle());
                        content = templateEngine.process("newTalent", context);

                        messageHelper.setText(content,true);
                    };

                    try {
                        sender.send(messagePreparator);
                    } catch (MailException e) {
                        e.printStackTrace();

                    }
                }

            }

    }


    @Override
    public void resetPassword(AppUser appUser){
        AppUserView appUserView = EntityToViewConverter.convertEntityToAppUserView(appUser);
        String newPassword = appUser.getPassword();
            //context template message

            Context context = new Context();
            context.setVariable("username", appUserView.getUsername());
            context.setVariable("frenchContent","Mot de passe réinitialisé avec succès ! Utilisez le nouveau mot de passe ci-dessous pour vous connecter. Ne le divulguez à personne !");
            context.setVariable("newPassword",newPassword);
            context.setVariable("englishContent","Password set successfully ! Use the passwword below for connected you. Don't show it at somebody !");
            context.setVariable("validate2",newPassword);
            context.setVariable("urlBackend",urlBackend);

            context.setVariable("date", DateUtils.dateTimeFormatter(0));

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse"));
                messageHelper.setTo(appUserView.getEmail());

                String content;
                messageHelper.setSubject("Alerte de sécurité !");
                content = templateEngine.process("resetPassword", context);

                messageHelper.setText(content,true);
            };

            try {
                sender.send(messagePreparator);
            } catch (MailException e) {
                e.printStackTrace();

            }
    }

    @Override
    public void testimonies(Testimony testimony) {

            TestimonyView view = EntityToViewConverter.convertEntityToTestimonyView(testimony);

            Context context = new Context();
            context.setVariable("username", view.getAuthorUsername());
            context.setVariable("frenchContent","Un avis vient d'être ajouté");
            context.setVariable("createdAt",DateUtils.dateTimeFormatter(0));
            context.setVariable("message",view.getMessage());

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse Advise"));
                messageHelper.setTo(emailAdmin);

                String content;
                messageHelper.setSubject("1 nouvel avis !");
                content = templateEngine.process("testimony", context);

                messageHelper.setText(content,true);
            };

            try {
                sender.send(messagePreparator);
            } catch (MailException e) {
                e.printStackTrace();

            }

    }




/*    @Override
    public void sendEmailRegister(AppUserView appUserView) {
        String url = "http://localhost:8085/api/auth/v1/checkEmail/";

        try {

            DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss Z");

            String date = LocalDateTime.now().atOffset(OffsetDateTime.now().getOffset()).format(FORMATTER) ;

//iwfwlqyirwvwfqkn
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            //setting up necessary detail
            mailMessage.setFrom(sendFrom);
            mailMessage.setTo(appUserView.getEmail());
            mailMessage.setSubject("Account Validation !");
            mailMessage.setText("Bienvenue sur Bozahouse, la maison des travailleurs. " +
                    "pour activer votre compte, cliquez sur le lien ci-dessous !\n" + url + appUserView.getEmail()
                    +"\n\n welcome on Bozahouse, house of workers. for activate your account, click on the link down !" +
                    "\n" +url + appUserView.getEmail() +"\n" +date );


            //sending the mail
            sender.send(mailMessage);
        }
        //catch the block to handle the exception
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void dailyGift() {

        List<Subscription> subscriptions = subscriptionRepo.findAllByActivatedTrueNotPageable();

        for (Subscription subscription : subscriptions){

            if (subscription.getBeneficiary().getNormalSubscriptionCounter()>0 || subscription.getBeneficiary().getHostSubscriptionCounter()>0){

            }

            Subscription updateSubscription = subscriptionRepo.findById(subscription.getId()).get();

            if (!subscription.isActivated() && !subscription.isLocked()){
                subscription.setActivated(true);
                subscription.setPeriod(updateSubscription.getPeriod() + Constant.dailySubscription);
                subscription.setCreatedAt(updateSubscription.getCreatedAt());
                subscription.setBeneficiary(updateSubscription.getBeneficiary());
                subscription.setUpdatedAt(DateUtils.currentDate());
                subscription.setAmount(250);
                subscription.setEndSubscription(DateUtils.currentDatePlus1Day());

                long diff_in_day = DateUtils.diff_in_days(DateUtils.currentDate(),subscription.getEndSubscription());

                subscription.setInitDuration(diff_in_day);
                subscription.setDailyAmount(subscription.getAmount()/ subscription.getInitDuration());
                subscription.setNumberOfUpdate(updateSubscription.getNumberOfUpdate() + 1);
                Subscription out = subscriptionRepo.save(subscription);
                AppUserView appUserView = EntityToViewConverter.convertEntityToAppUserView(out.getBeneficiary());

                log.info("send congratulate email");
                try {
                    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss Z");

                    String date = LocalDateTime.now().atOffset(OffsetDateTime.now().getOffset()).format(FORMATTER) ;
                    News news = newsRepo.listNewsNotPageable().get(0);

                    Context context = new Context();
                    context.setVariable("username", appUserView.getUsername());
                    context.setVariable("frenchContent",news.getFrenchContent());
                    context.setVariable("englishContent",news.getEnglishContent());
                    context.setVariable("date", date);

                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse"));
                        messageHelper.setTo(appUserView.getEmail());

                        String content;
                        messageHelper.setSubject(news.getSubject());
                        content = templateEngine.process("dailyGift", context);

                        messageHelper.setText(content,true);
                    };

                    try {
                        sender.send(messagePreparator);
                    } catch (MailException e) {
                        e.printStackTrace();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (subscription.isActivated() && !subscription.isLocked()) {
                subscription.setCreatedAt(updateSubscription.getCreatedAt());
                subscription.setBeneficiary(updateSubscription.getBeneficiary());
                subscription.setUpdatedAt(DateUtils.currentDate());
                subscription.setAmount(updateSubscription.getAmount() + 250);
                subscription.setEndSubscription(DateUtils.givenDatePlus1Day(updateSubscription.getEndSubscription()));

                long diff_in_day = DateUtils.diff_in_days(DateUtils.currentDate(),subscription.getEndSubscription());

                subscription.setInitDuration(diff_in_day);
                subscription.setDailyAmount(subscription.getAmount()/ subscription.getInitDuration());
                subscription.setNumberOfUpdate(updateSubscription.getNumberOfUpdate() + 1);
                subscription.setPeriod(updateSubscription.getPeriod());
                Subscription out = subscriptionRepo.save(subscription);
                AppUserView appUserView = EntityToViewConverter.convertEntityToAppUserView(out.getBeneficiary());
                log.info("send congratulate email");
                try {
                    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss Z");

                    String date = LocalDateTime.now().atOffset(OffsetDateTime.now().getOffset()).format(FORMATTER) ;


                    News news = newsRepo.listNewsNotPageable().get(0);

                    Context context = new Context();
                    context.setVariable("username", appUserView.getUsername());
                    context.setVariable("frenchContent",news.getFrenchContent());
                    context.setVariable("englishContent",news.getEnglishContent());
                    context.setVariable("date", date);

                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse"));
                        messageHelper.setTo(appUserView.getEmail());

                        String content;
                        messageHelper.setSubject(news.getSubject());
                        content = templateEngine.process("dailyGift", context);

                        messageHelper.setText(content,true);
                    };

                    try {
                        sender.send(messagePreparator);
                    } catch (MailException e) {
                        e.printStackTrace();

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

        }


    }*/


    /*
    @Override
    public void resetPassword(AppUserView appUserView) {
        String url = "http://localhost:8085/api/auth/v1/checkEmail/";
        try {
            DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss Z");

            String date = LocalDateTime.now().atOffset(OffsetDateTime.now().getOffset()).format(FORMATTER) ;

            //context template message

            Context context = new Context();
            context.setVariable("username", appUserView.getUsername());
            context.setVariable("frenchContent","Votre mot de passe a été réinitialiser avec succès!  !");
            context.setVariable("validate1",url + appUserView.getEmail());
            context.setVariable("englishContent","welcome on Bozahouse, house of workers. for activate your account, click on the link down !");
            context.setVariable("validate2",url + appUserView.getEmail());

            context.setVariable("date", date);

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse"));
                messageHelper.setTo(appUserView.getEmail());

                String content;
                messageHelper.setSubject("Account Validation !");
                content = templateEngine.process("validateAccount", context);

                messageHelper.setText(content,true);
            };

            try {
                sender.send(messagePreparator);
            } catch (MailException e) {
                e.printStackTrace();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

}

