package com.dindamaylan.tasku.utils;

import com.google.firebase.Timestamp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Helpers {
    public String getHashPassword(String originalString){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodeHash= digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodeHash);
        }catch (Exception e){
            return "";
        }
    }

    private static String bytesToHex(byte[] hash){
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b: hash){
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String formatTime(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance(new Locale("in", "ID"));
        calendar.setTime(timestamp.toDate());
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
        return format.format(calendar.getTime());
    }

    public Timestamp getAWeenToGo(){
        Calendar calendar = Calendar.getInstance(new Locale("in", "ID"));
        calendar.setTime(Timestamp.now().toDate());
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        return new Timestamp(calendar.getTime());
    }
}
