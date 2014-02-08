package com.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: Evgenia Simonova
 */
public class HashUtil {
    public static byte[] sha512(final byte[] message) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error");
        }
        return md.digest(message);
    }

    public static byte[] sha512(final String message) {
        return sha512(message.getBytes());
    }
}
