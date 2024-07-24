package io.github.pranav1344.totp.main;


import io.github.pranav1344.totp.totp.TOTPVerify;
import io.github.pranav1344.totp.util.SecretGenerator;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class OTPAuth {
    private String secret;
    int digits;
    int timeWindow;
    public OTPAuth(int digits, int timeWindow) {
        this.secret = SecretGenerator.generateSecret(20);
        this.digits = digits; 
        this.timeWindow = timeWindow;
    }
    public boolean verify(String input) {
        return TOTPVerify.verify(secret, digits, timeWindow, input);
    }
    public String getOTPAuthURL(String label, String issuer) {
        return "otpauth://totp/" +
                uriEncode(label) + "?" +
                "secret=" + uriEncode(secret) +
                "&issuer=" + uriEncode(issuer) +
                "&algorithm=" + uriEncode("SHA1") +
                "&digits=" + digits +
                "&period=" + timeWindow;
    }
    private String uriEncode(String text)  {
        if (text == null) {
            return "";
        }
        try {
            return URLEncoder.encode(text, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Could not generate the OTPAuth data.");
        }
    }
}
