package net.bozahouse.backend;

import net.bozahouse.backend.repositories.AppRoleRepo;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.services.AppUserService;
import net.bozahouse.backend.utils.LoadInitialData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.security.crypto.password.PasswordEncoder;



@EnableScheduling
@SpringBootApplication
public class BozahouseApplication {

	// about @Sheduled(cron = "0 1 1 * * ?") = (second, minute, hour, day of month, month, day(s) of week
//1-  "0 0 * * * *"   = the top of every hour of every day
//2-  "*/10 * * * * *"   = every ten seconds
//3-  "0 0 9-17 * * MON-FRI"   = on the hour nine to five weekdays
//4-  "0 0 8-10 * * *"   = 8, 9 and 10 o'clock of every day
//5-  "0 0 8,10 * * *"   = 8 and 10 o'clock of every day
//6-  "0 0/30 8-10 * * *"   = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day
//7-  "0 0 * 25 12 ?"   = every Christmas day at midnight
//8-  "0 0 6 * * *"   = every day at 6 hour
//9-  "0 1 1 * * *"   = every day at 1:01
// about @Sheduled(cron = "0 1 1 * * ?", zone="Europe/Paris") tenir compte du fuseau horaire pour que les heures ne soient pas eronne
//  (*) n'importe quel
//  */X tous les X
//  ? aucune valeur specifique

	public static void main(String[] args) {
		SpringApplication.run(BozahouseApplication.class, args);
	}


/*	@Bean
	public CommandLineRunner loadData(AppUserService appUserService, AppUserRepo appUserRepo, AppRoleRepo roleRepo, PasswordEncoder encoder){

		return args -> {
			if (appUserRepo.count() < 1) {
				LoadInitialData.loadAdminData(appUserService, appUserRepo, roleRepo, encoder);
			}
		};
	}*/




}
