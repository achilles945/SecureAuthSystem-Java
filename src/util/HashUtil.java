package util;

import java.security.MessageDigest;

public class HashUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    public static boolean verify(String input, String storedHash) {
        String inputHash = hashPassword(input);
        return inputHash.equals(storedHash);
    }
}
