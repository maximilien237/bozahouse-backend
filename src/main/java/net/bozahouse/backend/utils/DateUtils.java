package net.bozahouse.backend.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtils {

private DateUtils(){
    throw new IllegalStateException("date utils");
}

public static String dateTimeFormatter(long period) {

    try {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss Z");

        return LocalDateTime.now().plusDays(period).atOffset(OffsetDateTime.now().getOffset()).format(formatter);

    } catch (Exception e) {
        e.printStackTrace();

    }
    return null;

}



}
