package net.bozahouse.backend.utils;

import java.util.UUID;

public class RandomUtils {

    public static String unique(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String id(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static int generate5Int(){
        int min = 10001, max = 100000;
        return  (int) (Math.random()*(max - min+1) + min);
    }

    public static int generateMin3Int(){
        int min = 100, max = 999;
        return  (int) (Math.random()*(max - min+1) + min);
    }

    public static int generate2Int(){
        int min = 10, max = 99;
        return  (int) (Math.random()*(max - min+1) + min);

    }
}
