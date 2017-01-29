package com.example.android.fusion_tesis.Modelo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Edgar on 23/12/2016.
 */

public class Textgenerator {

    public static String generateSalt(){
        /** Generate a random String
         * @return Random String;
         */
        String salt="";
        char l;
        for(int i=0; i<16; i++){
             int ran = (int)(Math.random()*93)+33;

            l=(char)ran;
                salt=salt+l;

        }

        return salt;
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash, String   salt){
        /** Generate a hash String
         * @param passwordToHash pass to changue, salt the salt to use
         * @return a String
         */
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }


}
