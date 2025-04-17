package removing.dependencies;

import java.util.Random;

public class TestEscapers {

    public static void main(String[] args) {
        testUrlEscaper();
        for (int i = 0; i < 10; i++) {
            testPerformance(new EscaperJDK(), "custom jdk impl");
        }
        for (int i = 0; i < 10; i++) {
            testPerformance(GuavaEscapers.urlPathSegmentEscaper(), "guava impl");
        }
    }

    private static void testPerformance(Escaper escaper, String escaperName) {
        long startTime = System.nanoTime();
        int iters = 0;
        for (int length = 1; length < 100; length++) {
            String toEscape = generateRandomString(length);
            for (int i = 0; i < 10000; i++) {
                escaper.escape(toEscape);
                iters++;
            }
        }
        System.out.println("One escape operation with " + escaperName + " takes " + (System.nanoTime() - startTime) / iters + "ns");
    }

    private static void testUrlEscaper() {
        System.out.println("== TestEscapers Url escaper ==");
        String test = "racadabra 1234 cucu-Cucu  !>";
        System.out.println("Input string is: " + test);
        EscaperJDK escaperJDK = new EscaperJDK();
        Escaper guavaEscaper = GuavaEscapers.urlPathSegmentEscaper();
        System.out.println("EscaperJDK    : " + escaperJDK.escape(test));
        System.out.println("GuavaEscapers : " + guavaEscaper.escape(test));
    }

    public static String generateRandomString(int size) {
        final String from = "abcdefghij      123456789" + EscaperJDK.SPECIAL_CHARS;
        final Random rnd = new Random();

        StringBuilder rez = new StringBuilder();
        for (int cnt = 0; cnt < size; cnt++) {
            rez.append(from.charAt(rnd.nextInt(from.length())));
        }

        return rez.toString();
    }
}
