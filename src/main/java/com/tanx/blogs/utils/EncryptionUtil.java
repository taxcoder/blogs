package com.tanx.blogs.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtil {
    private static String code = null;
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static String md5(String password) {
        return md5(password, 5);
    }

    public static String md5(String password, int length) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.reset();
            byte[] digest = md5.digest(password.getBytes());
            for (byte d : digest) {
                String temp = Integer.toHexString(d & 0xFF);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            code = encoder(result.toString(), length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String simpleMD5(String password) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.reset();
            byte[] digest = md5.digest(password.getBytes());
            for (byte d : digest) {
                String temp = Integer.toHexString(d & 0xFF);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String encoder(byte[] digest, int length) {
        for (int i = 0; i < length; i++) {
            digest = encoder.encode(digest);
        }
        return new String(digest);
    }

    public static String encoder(byte[] digest) {
        return encoder(digest, 5);
    }

    public static String encoder(String digest, int length) {
        byte[] data = digest.getBytes();
        for (int i = 0; i < length; i++) {
            data = encoder.encode(data);
        }
        return new String(data);
    }

    public static String encoder(String digest) {
        return encoder(digest, 5);
    }

    public static String decoder(byte[] digest, int length) {
        for (int i = 0; i < length; i++) {
            digest = decoder.decode(digest);
        }
        return new String(digest);
    }

    public static String decoder(byte[] digest) {
        return decoder(digest, 5);
    }

    public static String decoder(String digest, int length) {
        byte[] data = digest.getBytes();
        for (int i = 0; i < length; i++) {
            data = decoder.decode(data);
        }
        return new String(data);
    }

    public static byte[] decoderByte(String digest, int length) {
        byte[] data = digest.getBytes();
        for (int i = 0; i < length; i++) {
            data = decoder.decode(data);
        }
        return data;
    }

    public static String decoder(String digest) {
        return decoder(digest, 5);
    }
}
