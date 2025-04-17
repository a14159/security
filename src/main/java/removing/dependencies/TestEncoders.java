package removing.dependencies;

import java.util.Random;

public class TestEncoders {

    public static void main(String[] args) {
        testBase32Hex();
        testBase32();
        testBase16();
        testBase64();

        System.out.println("=== Base 16 performance testing ===");
        testPerformance(GuavaBaseEncoding.base16(), "base16 guava");
        testPerformance(BaseEncoding.base16(), "base 16custom impl");

        System.out.println("=== Base 32 performance testing ===");
        testPerformance(GuavaBaseEncoding.base32(), "base32 guava");
        testPerformance(BaseEncoding.base32(), "base32 custom impl");

        System.out.println("=== Base 32Hex performance testing ===");
        testPerformance(GuavaBaseEncoding.base32Hex(), "base32hex guava");
        testPerformance(BaseEncoding.base32Hex(), "base32hex custom impl");

        System.out.println("=== Base 64 performance testing ===");
        testPerformance(GuavaBaseEncoding.base64(), "base64 guava");
        testPerformance(BaseEncoding.base64(), "base64 custom impl");

        System.out.println("=== Base 64 url performance testing ===");
        testPerformance(GuavaBaseEncoding.base64Url(), "base64url guava");
        testPerformance(BaseEncoding.base64Url(), "base64url custom impl");
    }

    private static void testPerformance(Encoder encoder, String encoderName) {
        for (int i = 0; i < 10; i++) {
            testSimplePerformance(encoder, encoderName);
        }
    }

    private static void testSimplePerformance(Encoder encoder, String encoderName) {
        long startTime = System.nanoTime();
        int iters = 0;
        for (int length = 1; length < 100; length++) {
            byte[] toEncode = generateRandomString(length).getBytes();
            for (int i = 0; i < 10000; i++) {
                encoder.encode(toEncode);
                iters++;
            }
        }
        System.out.println("One encode operation with " + encoderName + " takes " + (System.nanoTime() - startTime) / iters + "ns");
    }

    private static void testBase16() {
        System.out.println("== TestEncoders Base16 ==");
        String test = "cadabra 1234 cucu-Cucu  !>";
        Encoder base16 = new Base16(true);
        GuavaBaseEncoding baseEncoding = GuavaBaseEncoding.base16().lowerCase();
        System.out.println("Input string is:      " + test);
        System.out.println("Base16 encoded:       " + base16.encode(test.getBytes()));
        System.out.println("GuavaBase16 encoded:  " + baseEncoding.encode(test.getBytes()));
        System.out.println("Base16 decoded:       " + new String(base16.decode(base16.encode(test.getBytes()))));
    }

    private static void testBase32() {
        System.out.println("== TestEncoders Base32 ==");
        String test = "racadabra 1234 cucu-Cucu  !>";
        Base32 base32 = new Base32(true, true);
        GuavaBaseEncoding baseEncoding = GuavaBaseEncoding.base32().lowerCase();
        System.out.println("Input string is:      " + test);
        System.out.println("Base32 encoded:       " + base32.encode(test.getBytes()));
        System.out.println("GuavaBase32 encoded:  " + baseEncoding.encode(test.getBytes()));
        System.out.println("Base32 decoded:       " + new String(base32.decode(base32.encode(test.getBytes()))));
    }

    private static void testBase32Hex() {
        System.out.println("== TestEncoders Base32Hex ==");
        String test = "bracadabra 1234 cucu-Cucu  !>";
        System.out.println("Input string is: " + test);
        Base32Hex base32Hex = new Base32Hex(true, true);
        GuavaBaseEncoding baseEncoding = GuavaBaseEncoding.base32Hex().lowerCase();
        System.out.println("Base32Hex encoded:      " + base32Hex.encode(test.getBytes()));
        System.out.println("GuavaBase32hex encoded: " + baseEncoding.encode(test.getBytes()));
        System.out.println("Base32Hex decoded:      " + new String(base32Hex.decode(base32Hex.encode(test.getBytes()))));
    }

    private static void testBase64() {
        System.out.println("== TestEncoders Base64 ==");
        String test = "bracadabra 1234 cucu-Cucu  !>";
        System.out.println("Input string is: " + test);
        Base64 base64 = new Base64(true, true);
        GuavaBaseEncoding baseEncoding = GuavaBaseEncoding.base64Url();
        System.out.println("Base64 encoded:       " + base64.encode(test.getBytes()));
        System.out.println("GuavaBase64 encoded:  " + baseEncoding.encode(test.getBytes()));
        System.out.println("Base64 decoded:       " + new String(base64.decode(base64.encode(test.getBytes()))));
    }

    public static String generateRandomString(int size) {
        final String from = "abcdefghij      123456789>><<||!!" + EscaperURLPath.SPECIAL_CHARS;
        final Random rnd = new Random();

        StringBuilder rez = new StringBuilder();
        for (int cnt = 0; cnt < size; cnt++) {
            rez.append(from.charAt(rnd.nextInt(from.length())));
        }

        return rez.toString();
    }
}
