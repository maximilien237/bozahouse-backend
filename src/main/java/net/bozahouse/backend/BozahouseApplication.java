package net.bozahouse.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.bozahouse.backend.utils.DateUtils;


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


		//System.out.println(DateUtils.dateTimeFormatter(10));

/*		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		try {
			Date date1 = sdf.parse("2022-12-17");
			Date date2 = sdf.parse("2022-12-22");
			long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
			long result = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			boolean b = new Date().before(date1);
			boolean b1 = new Date().before(date2);
			System.out.println(result +" "+b +" "+b1);


		} catch (ParseException parseException){
			parseException.getStackTrace();
		}*/



	}

}
