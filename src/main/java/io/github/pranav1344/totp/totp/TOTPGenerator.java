package io.github.pranav1344.totp.totp;


import io.github.pranav1344.totp.util.Base32;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class TOTPGenerator {
    private static final int[] DIGITS_POWER
     = {1,10,100,1000,10000,100000,1000000,10000000,100000000 };
    private static byte[] GenerateSHAHash(String secret, long timer, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac messagecode = algorithm.equals("SHA1") ? Mac.getInstance("HmacSHA1") : Mac.getInstance("HmacSHA256");
        byte[] secretInByte = Base32.decode(secret);
        byte[] data = new byte[8];
        long value = timer;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        messagecode.init(new SecretKeySpec(secretInByte, algorithm.equals("SHA1") ? "SHA1" : "SHA-256"));
        return messagecode.doFinal(data);
    }
    
    private static String getCodeFromHash(byte[] hash, int digits) {
        int offset = hash[hash.length - 1] & 0xF;
        long binary = ((hash[offset] & 0x7f) << 24) |
             ((hash[offset + 1] & 0xff) << 16) |
             ((hash[offset + 2] & 0xff) << 8) |
             (hash[offset + 3] & 0xff);
        binary %= DIGITS_POWER[digits];
        return String.format("%0" + digits + "d", binary);
    }
    public static String[] getTOTP(String secret, int digits, int timeWindow, String algorithm) {
        long currentTimeWindow = Math.floorDiv(Instant.now().getEpochSecond(), timeWindow);
        try {
            return  new String[]{getCodeFromHash(GenerateSHAHash(secret, currentTimeWindow - 1, algorithm), digits), getCodeFromHash(GenerateSHAHash(secret, currentTimeWindow, algorithm), digits), getCodeFromHash(GenerateSHAHash(secret, currentTimeWindow + 1, algorithm), digits)};
        } catch (InvalidKeyException | NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error while generating OTP", ex);
        }
    }
}
