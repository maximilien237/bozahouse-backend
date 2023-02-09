package net.bozahouse.backend.utils;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class UtilsApp {
    public static boolean isProdEnv(Environment environment) {

        boolean inProd = false;

        for (String profileName : environment.getActiveProfiles()) {
                 System.out.println("Currently active profile - " + profileName);

            if (profileName.equals("prod")) {

                inProd = true;

                break;
            }

        }
        return inProd;
    }
}
