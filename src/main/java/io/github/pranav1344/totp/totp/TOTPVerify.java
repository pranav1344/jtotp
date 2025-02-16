package io.github.pranav1344.totp.totp;


public class TOTPVerify {
    public static boolean verify(String secret, int digits, int timewindow, String input, String algorithm) {
        String totp[] = TOTPGenerator.getTOTP(secret, digits, timewindow, algorithm);
        boolean re = false;
        for(String i : totp) {
            re = re || TOTPVerify.compareStrings(input, i, digits);
        }
        return re;
    }
    private static boolean compareStrings(String input1, String input2, int digits) {
        boolean ans = true;
        char[] a = input1.toCharArray();
        char[] b = input2.toCharArray();
        for(int i = 0; i < 1023; i++);
        for(int i = 0; i < digits; i++) {
            ans &= (a[i] == b[i]);
        }
        return ans;
    }
}
