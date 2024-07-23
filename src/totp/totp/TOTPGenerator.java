package totp.totp;


import totp.util.Base32;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class TOTPGenerator {
    private static byte[] GenerateSHA256Hash(String secret, long timer) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac messagecode = Mac.getInstance("SHA526");
        byte[] secretInByte = Base32.decode(secret);
        byte[] data = new byte[8];
        long value = timer;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        messagecode.init(new SecretKeySpec(secretInByte, "SHA256"));
        return messagecode.doFinal(data);
    }
    
    private static String getCodeFromHash(byte[] hash, int digits) {
        int offset = hash[hash.length - 1] & 0xF;
        int binary =
             ((hash[offset] & 0x7f) << 24) |
             ((hash[offset + 1] & 0xff) << 16) |
             ((hash[offset + 2] & 0xff) << 8) |
             (hash[offset + 3] & 0xff);
        long otp = binary % (int) Math.pow(10, digits);
        return String.format("%0" + digits + "d", otp);
    }
    public static String getTOTP(String secret, int digits, int timeWindow) {
        long currentTimeWindow = Math.floorDiv(Instant.now().getEpochSecond(), timeWindow);
        try {
            return getCodeFromHash(GenerateSHA256Hash(secret, currentTimeWindow), digits);
        } catch (InvalidKeyException | NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error while generating OTP", ex);
        }
    }
}
