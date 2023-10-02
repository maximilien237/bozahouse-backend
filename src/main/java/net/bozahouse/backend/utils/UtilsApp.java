package net.bozahouse.backend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtilsApp {
    private UtilsApp() {}
    public static boolean isProdEnv(Environment environment) {

        boolean inProd = false;

        for (String profileName : environment.getActiveProfiles()) {
                 log.info("Currently active profile - " + profileName);

            if (profileName.equals("prod")) {

                inProd = true;

                break;
            }

        }
        return inProd;
    }
}
