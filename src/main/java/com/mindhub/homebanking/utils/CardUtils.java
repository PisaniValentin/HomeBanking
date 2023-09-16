package com.mindhub.homebanking.utils;

import java.util.Random;

public final class  CardUtils {
    private CardUtils(){}

    public static String getCardNumber() {
        Random random = new Random();
        int randomNumber1 = random.nextInt(10000);
        int randomNumber2 = random.nextInt(10000);
        int randomNumber3 = random.nextInt(10000);
        int randomNumber4 = random.nextInt(10000);
        String formattedNumber1 = String.format("%04d",randomNumber1);
        String formattedNumber2 = String.format("%04d",randomNumber2);
        String formattedNumber3 = String.format("%04d",randomNumber3);
        String formattedNumber4 = String.format("%04d",randomNumber4);
        return formattedNumber1+"-"+formattedNumber2+"-"+formattedNumber3+"-"+formattedNumber4;
    }

    public static int getCVV(){
        Random random = new Random();
        int cvvNumber = random.nextInt(1000);
        return  Integer.parseInt((String.format("%03d",cvvNumber)));
    }
}
