package net.bozahouse.backend.config;


//@Configuration
public class LoadDatabase {


       // @Bean
/*        CommandLineRunner start(AppUserRepo userRepo,AppUserService userService,AppRoleService roleService,OfferService offerService, PasswordEncoder encoder){
                return args -> {

                        AppUser appUser = new AppUser();
                };
        }*/

/*
                AppUserForm form = new AppUserForm();
                form.setUsername("maximilien");
                form.setLastname("kengne");
                form.setAccount("employer");
                form.setBirthday(DateUtils.convertSimpleDateFormat("17-12-1992 01:10:20"));
                form.setHowKnowUs("conceptor");
                form.setFirstname("Maximilien");
                form.setPassword("1234");
                form.setConfirmPassword("1234");
                form.setReferralCode("none");
                form.setEmail("max@gmail.com");
                AppUser appUser = FormToEntityConverter.convertFormToAppUser(form);
                appUser.setId(RandomUtils.unique());
                appUser.setActivated(true);
                appUser.setCreatedAt(new Date());


                    appUserRepo.save(appUser);
                    appUserService.addRoleToUser("maximilien",ERole.ROLE_ROOT);
                    appUserService.addRoleToUser("maximilien",ERole.ROLE_ADMIN);
                    appUserService.addRoleToUser("maximilien",ERole.ROLE_EDITOR);
                    appUserService.addRoleToUser("maximilien",ERole.ROLE_EMPLOYER);
                    appUserService.addRoleToUser("maximilien",ERole.ROLE_FIRM);
                    appUserService.addRoleToUser("maximilien",ERole.ROLE_TALENT);

*/



/*            Stream.of("marc", "vidal", "justin").forEach(name -> {
                AppUserForm form = new AppUserForm();
                form.setUsername(name);
                form.setLastname("name");
                form.setAccount("employer");
                form.setBirthday(DateUtils.convertSimpleDateFormat("17-12-1999 01:10:20"));
                form.setHowKnowUs("facebook");
                form.setFirstname("surname");
                form.setPassword("1234");
                form.setConfirmPassword("1234");
                form.setReferralCode("cake");
                form.setEmail(name + "@gmail.com");
                AppUser appUser = FormToEntityConverter.convertFormToAppUser(form);
                try {
                    ThreadUtils.sleep5();
                    appUserService.createAppUser(appUser);


                } catch (RoleNotFoundException | AppUserNotFoundException | PaymentNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            appUserRepo.findAll().forEach(appUser -> {
                OfferForm form = new OfferForm();
                form.setAddress("ekounou");
                form.setCity(city);
                form.setDateExpiration(DateUtils.convertSimpleDateFormat("13-10-2023 01:10:20"));
                form.setDomain("Auto-école");
                form.setId(RandomUtils.unique());
                form.setTitle("Chauffeur");
                form.setCountry(country);
                form.setDomain("Elevage");
                form.setWhatsAppNumber("67984523");
                form.setName("job home");
                Offer offer = FormToEntityConverter.convertFormToOffer(form);
                offer.setUser(appUser);


                Talent talent = new Talent();
                talent.setCity(city1);
                talent.setSalary("23000.0");
                talent.setPublishedAt(new Date());
                talent.setDomain("informatique");
                talent.setTitle("dev web");
                talent.setSex("M.");
                talent.setUser(appUser);


                offerService.createOfferView(offer);
                offerService.createOfferView(offer);
                talentService.createTalentView(talent);


            });*/
        };
/*    }
}*/

