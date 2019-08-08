/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.common.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;

/**
 *
 * @author emma
 */
public class SecurityHash {
    public static synchronized String MD5HashEncryption(String plaintext) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5"); //step 2
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(SecurityHash.class.getName()).log(Level.SEVERE, e.toString());
            return plaintext;
        }

        try {
            md.update(plaintext.getBytes("UTF-8")); //step 3
        } catch (UnsupportedEncodingException e) {
            System.out.println("SecurityHash::hash() UnsupportedEncodingException: " + e);
            return plaintext;
        }

        byte byteData[] = md.digest(); //step 4

        //convert the byte to hex format method 1   ::  step 5
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        //System.out.println("Digest(in hex format):: " + sb.toString());

        //convert the byte to hex format method 2   ::   step 6
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        //String hash = (new BASE64Encoder()).encode(raw); //step 5
        //return hash; //step 6

        return hexString.toString();
    }
}
