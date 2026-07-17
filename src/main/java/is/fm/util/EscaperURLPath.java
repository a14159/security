package is.fm.util;

public class EscaperURLPath implements Escaper {

    public static final String SPECIAL_CHARS = " %$&+,/:;=?@<>#%";

    private static final String[] encodeTable = new String[256];

    static {
        for (char ch = 0; ch < 256; ch++) {
            if (ch > 128 || SPECIAL_CHARS.indexOf(ch) >= 0) {
                encodeTable[ch] = "%" + toHex(ch / 16) + toHex(ch % 16);
            }
        }
    }

    private static char toHex(int ch) {
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }

    @Override
    public String escape(String input) {
//        return URLEncoder.encode(arg, StandardCharsets.UTF_8); // this encored uses "+" for spaces
        final String[] encodeTableCopy = encodeTable;
        final int inputLength = input.length();
        int firstEscapeIndex = 0;
        while (firstEscapeIndex < inputLength
                && encodeTableCopy[input.charAt(firstEscapeIndex)] == null) {
            firstEscapeIndex++;
        }
        if (firstEscapeIndex == inputLength) {
            return input;
        }

        final StringBuilder resultStr = new StringBuilder(3 * inputLength / 2);
        resultStr.append(input, 0, firstEscapeIndex);
        for (int i = firstEscapeIndex; i < inputLength; i++) {
            char ch = input.charAt(i);
            String encodedCh = encodeTableCopy[ch];
            if (encodedCh != null) {
                resultStr.append(encodedCh);
            } else {
                resultStr.append(ch);
            }
        }
        return resultStr.toString();
    }
}
