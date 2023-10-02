package net.bozahouse.backend.services.email;

import jakarta.mail.internet.InternetAddress;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.dtos.*;
import net.bozahouse.backend.model.entities.*;
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


import java.util.Date;
import java.util.List;




@Service
@Slf4j
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

    public void sendNewsletter(NewsletterDTO newsletterDTO) {
        List<AppUser> appUsers = userRepo.findAllByActivatedTrue();
        List<AppUserDTO> appUserDTOS = appUsers.stream().map(AppUserDTO::mapToDTO).toList();
        //context template message
        for (AppUserDTO appUserDTO : appUserDTOS){

            Context context = new Context();
            context.setVariable("username", appUserDTO.getUsername());
            context.setVariable("frenchContent",newsletterDTO.getFrenchContent());
            context.setVariable("englishContent",newsletterDTO.getEnglishContent());
            context.setVariable("date", DateUtils.dateTimeFormatter(0));

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse news"));
                messageHelper.setTo(appUserDTO.getEmail());

                String content;
                messageHelper.setSubject(newsletterDTO.getSubject());
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
    public void newsletters(NewsletterDTO newsletterDTO) {
        sendNewsletter(newsletterDTO);

    }

    @Override
    public void newslettersPlan() {
        List<Newsletter> newsletters = newsletterRepo.findAllBySendingDate(new Date());

        newsletters.forEach(newsletter -> {
            if (!newsletters.isEmpty()){
                //context template message

                sendNewsletter(NewsletterDTO.mapToDTO(newsletter));
            }
        });


    }

    @Override
    public void validateAccount(AppUser appUser) {
        String url = urlBackend + "/api/auth/v1/checkEmail/";
        //context template message

        AppUserDTO appUserDTO = AppUserDTO.mapToDTO(appUser);

        Context context = new Context();
        context.setVariable("username", appUserDTO.getUsername());
        context.setVariable("frenchContent","Bienvenue sur Bozahouse, la maison des travailleurs. pour activer votre compte, cliquez sur le lien ci-dessous !");
        context.setVariable("validate1",url + appUserDTO.getEmail());
        context.setVariable("englishContent","welcome to Bozahouse, house of workers. for activate your account, click on the link down !");
        context.setVariable("validate2",url + appUserDTO.getEmail());

        context.setVariable("date", DateUtils.dateTimeFormatter(0));

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(new InternetAddress(sendFrom, "Bozahouse"));
            messageHelper.setTo(appUserDTO.getEmail());

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
    public void jobNotification(OfferDTO offerDTO) {

        List<AppUser> appUsers = userRepo.findAllByAccountContainingAndActivatedTrue("talent");

        List<AppUserDTO> appUserDTOS = appUsers.stream().map(AppUserDTO::mapToDTO).toList();

        String url = "https://www.bozahouse.com/";
        //context template message

        for (AppUserDTO appUserDTO : appUserDTOS){

            Context context = new Context();
            context.setVariable("username", appUserDTO.getUsername());
            context.setVariable("frenchContent", "Une offre d'emploi vient d'être ajouté.");
            context.setVariable("see1", url);
            context.setVariable("englishContent", "A offer come to be added.");
            context.setVariable("see2", url);
            context.setVariable("date", DateUtils.dateTimeFormatter(0));

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(new InternetAddress(sendFrom, "Alertes Bozahouse Jobs"));
                messageHelper.setTo(appUserDTO.getEmail());

                String content;
                messageHelper.setSubject("une offre d'emploi pour " + offerDTO.getTitle());
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
    public void talentNotification(TalentDTO talentDTO) {
        List<AppUser> appUsers = userRepo.findAllByActivatedTrue();
        List<AppUserDTO> appUserDTOS = appUsers.stream().map(AppUserDTO::mapToDTO).toList();

        String url = "https://www.bozahouse.com/";
        //context template message

        for (AppUserDTO appUserDTO : appUserDTOS){
            if (appUserDTO.getAccount().equalsIgnoreCase("employer") || appUserDTO.getAccount().equalsIgnoreCase("enterprise")){
                Context context = new Context();
                context.setVariable("username", appUserDTO.getUsername());
                context.setVariable("frenchContent","Un talent vient d'être ajouté.");
                context.setVariable("see1",url);
                context.setVariable("englishContent","A talent come to be added.");
                context.setVariable("see2",url);
                context.setVariable("date", DateUtils.dateTimeFormatter(0));

                MimeMessagePreparator messagePreparator = mimeMessage -> {
                    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                    messageHelper.setFrom(new InternetAddress(sendFrom, "Alertes Bozahouse Talents"));
                    messageHelper.setTo(appUserDTO.getEmail());

                    String content;
                    messageHelper.setSubject("1 talent pour " + talentDTO.getTitle());
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
    public void testimonies(TestimonyDTO testimonyDTO) {

        Context context = new Context();
        context.setVariable("username", testimonyDTO.getAuthorUsername());
        context.setVariable("frenchContent","Un avis vient d'être ajouté");
        context.setVariable("createdAt",DateUtils.dateTimeFormatter(0));
        context.setVariable("message",testimonyDTO.getMessage());

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

}

