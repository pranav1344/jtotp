package totp.util;


import java.security.SecureRandom;


public class SecretGenerator {
    public static String generateSecret(int length) {
        byte[] secret = new byte[(length * 5) / 8];
        new SecureRandom().nextBytes(secret);
        return Base32.encode(secret);
    }
}
