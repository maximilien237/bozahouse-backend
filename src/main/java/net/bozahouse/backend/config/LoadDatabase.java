package net.bozahouse.backend.config;


//@Configuration
public class LoadDatabase {


        // @Bean
/*         CommandLineRunner start(AppUserRepo appUserRepo, OfferService offerService,
                                 TalentService talentService, UserDetailsServiceImpl userDetailsService,
                                 NewsletterService newsletterService, TestimonyService testimonyService,
                                 PasswordEncoder encoder)
         {
                return args -> {


                    AppUserForm form = new AppUserForm();
                    form.setUsername("admin");
                    form.setLastname("ADMIN");
                    form.setAccount("employer");
                    form.setBirthday(DateUtils.convertDateToString(new Date()));
                    form.setHowKnowUs("other");
                    form.setFirstname("Admin");
                    form.setPassword(encoder.encode("admin"));
                    form.setConfirmPassword(encoder.encode("admin"));
                    form.setReferralCode("none");
                    form.setEmail("admin@gmail.com");
                    form.setAcceptTerms(true);
                    form.setSex("Homme");
                    AppUser user = FormToEntityConverter.convertFormToAppUser(form);
                    user.setId(RandomUtils.unique());
                    user.setActivated(true);
                    user.setCreatedAt(new Date());
                    user.setFirstConnexion(false);

                    userDetailsService.saveAdmin(user);



                    Stream.of("marc", "vidal", "user","luc", "bob", "max","cooper", "denver", "maxi").forEach(name -> {
                        AppUserForm form1 = new AppUserForm();
                        form1.setUsername(name);
                        form1.setLastname("name");
                        form1.setAccount("talent");
                        form1.setBirthday(DateUtils.convertDateToString(new Date()));
                        form1.setHowKnowUs("facebook");
                        form1.setFirstname("surname");
                        form1.setPassword(encoder.encode("1234"));
                        form1.setConfirmPassword(encoder.encode("1234"));
                        form1.setReferralCode("none");
                        form1.setEmail(name + "@gmail.com");
                        form1.setAcceptTerms(true);
                        form1.setSex("Homme");
                        AppUser appUser = FormToEntityConverter.convertFormToAppUser(form1);
                        appUser.setActivated(true);
                        appUser.setCreatedAt(new Date());
                        appUser.setFirstConnexion(false);

                        userDetailsService.saveAppUser(appUser);


                    });



                    appUserRepo.findAll().forEach(appUser -> {


                        NewsletterForm newsletterForm = new NewsletterForm();
                        newsletterForm.setSubject("Bozahouse news");
                        newsletterForm.setEnglishContent("english");
                        newsletterForm.setFrenchContent("french");
                        Newsletter newsletter = FormToEntityConverter.convertFormToNews(newsletterForm);
                        newsletter.setCreatedAt(new Date());
                        newsletter.setUser(appUser);
                        //save
                        newsletterService.createNews(newsletter);

                        TestimonyForm testimonyForm = new TestimonyForm();
                        testimonyForm.setMessage("great");
                        Testimony testimony = FormToEntityConverter.convertFormToTestimony(testimonyForm);
                        testimony.setAuthor(appUser);
                        testimony.setCreatedAt(new Date());
                        //save
                        testimonyService.createTestimony(testimony);




                        OfferForm form2 = new OfferForm();
                        form2.setFcb("https://maven.apache.org/xsd/maven-4.0.0.xsd");
                        form2.setLinkedin("https://maven.apache.org/xsd/maven-4.0.0.xsd");
                        form2.setContract("CDI");
                        form2.setDomain("Auto-école");
                        form2.setTitle("Chauffeur");
                        form2.setExperience("+20 ans");
                        form2.setMission("bla bla bla");
                        form2.setWeb("https://maven.apache.org/xsd/maven-4.0.0.xsd");
                        form2.setWorkMode("A distance");
                        form2.setNeedPeople(1);
                        form2.setProfile("bla bla bla");
                        form2.setSalary("150000");
                        form2.setSalaryChoice("bla bla bla");
                        form2.setSkills("bla bla bla");
                        form2.setEmail("apache@gmail.com");
                        form2.setTel("67984523");
                        form2.setType("enterprise");
                        form2.setDomain("Elevage");
                        form2.setWhatsAppNumber("67984523");
                        form2.setName("Apache");
                        form2.setAddress("Ekounou, Yaoundé, Cameroun");
                        Offer offer = FormToEntityConverter.convertFormToOffer(form2);

                        offer.setEndOffer(DateUtils.currentDatePlus1Month());
                        offer.setUser(appUser);
                        try {
                            offerService.createOfferView(offer);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }


                        TalentForm form3 = new TalentForm();
                        form3.setEmail("talent@gmail.com");
                        form3.setTel("656453412");
                        form3.setType("professional");
                        form3.setGithub("https://maven.apache.org/xsd/maven-4.0.0.xsd");
                        form3.setContract("CDD");
                        form3.setExperience("Aucune");
                        form3.setAddress("Baf, Bafoussam, Cameroun");
                        form3.setLinkedin("https://maven.apache.org/xsd/maven-4.0.0.xsd");
                        form3.setSalaryChoice("");
                        form3.setSkills("bla bla bla");
                        form3.setWorkMode("Sur Site");
                        form3.setLevel("BAC+2");
                        form3.setWhatsAppNumber("656453412");
                        form3.setDomain("informatique");
                        form3.setTitle("dev web");
                        form3.setSalary("70000");

                        Talent talent = FormToEntityConverter.convertFormToTalent(form3);
                        talent.setUser(appUser);
                        talent.setPublishedAt(new Date());

                        talentService.createTalentView(talent);
                });

                };



        }*/
    }


