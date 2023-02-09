package net.bozahouse.backend.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class DateUtils {

private DateUtils(){
    throw new IllegalStateException("date utils");
}

public static String dateTimeFormatter(long period){

    try {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss Z");

        return LocalDateTime.now().plusDays(period).atOffset(OffsetDateTime.now().getOffset()).format(formatter);

    }catch (Exception e) {
            e.printStackTrace();

        }
    return null;

}

    //calculate time difference in days
 /*   public static long diff_in_days(Date startDate, Date endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);

    }*/


    public static long diff_in_days(Date startDate, Date endDate) {

        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

    }

    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
            return sdf.parse(date);
    }

    public static String convertDateToString(Date date) {
        String pattern = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(pattern);

        return dateFormat.format(date);
    }

    public static Date givenDatePlus1Day(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
    public static Date givenDatePlus1Week(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    public static Date givenDatePlus1Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public static Date givenDatePlus2Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 2);
        return calendar.getTime();
    }

    public static Date givenDatePlus3Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 3);
        return calendar.getTime();
    }

    public static Date givenDatePlus4Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 4);
        return calendar.getTime();
    }

    public static Date givenDatePlus5Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 5);
        return calendar.getTime();
    }

    public static Date givenDatePlus6Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 6);
        return calendar.getTime();
    }


    public static Date givenDatePlus7Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 7);
        return calendar.getTime();
    }

    public static Date givenDatePlus8Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 8);
        return calendar.getTime();

    }

    public static Date givenDatePlus9Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 9);
        return calendar.getTime();
    }

    public static Date givenDatePlus10Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 10);
        return calendar.getTime();
    }


    public static Date givenDatePlus11Month(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.MONTH, 11);
        return calendar.getTime();
    }

    public static Date givenDatePlus1Year(Date givenDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }

    public static Date currentDate(){
        Date today = Calendar.getInstance().getTime();
     return today;
    }
//"[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{3,4}"

    //current Date minus
    public static Date currentDateMinus1Day(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date currentDateMinus3Days(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -3);
        return calendar.getTime();
    }

    public static Date currentDateMinus1Week(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7);
        return calendar.getTime();
    }

    public static Date currentDateMinus2Weeks(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -14);
        return calendar.getTime();
    }

    public static Date currentDateMinus3Weeks(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -21);
        return calendar.getTime();
    }
    public static Date currentDateMinus1Month(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }


    //current date plus
    public static Date currentDatePlus1Day(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date currentDatePlus1Week(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();

    }

    public static Date currentDatePlus1Month(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public static Date currentDatePlus2Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 2);
        return calendar.getTime();
    }

    public static Date currentDatePlus3Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 3);
        return calendar.getTime();
    }

    public static Date currentDatePlus4Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 4);
        return calendar.getTime();
    }

    public static Date currentDatePlus5Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 5);
        return calendar.getTime();
    }

    public static Date currentDatePlus6Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 6);
        return calendar.getTime();
    }

    public static Date currentDatePlus7Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 7);
        return calendar.getTime();
    }

    public static Date currentDatePlus8Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 8);
        return calendar.getTime();
    }

    public static Date currentDatePlus9Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 9);
        return calendar.getTime();
    }

    public static Date currentDatePlus10Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 10);
        return calendar.getTime();
    }

    public static Date currentDatePlus11Months(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 11);
        return calendar.getTime();
    }

    public static Date currentDatePlus1Year(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }

    //Date conversion




}
