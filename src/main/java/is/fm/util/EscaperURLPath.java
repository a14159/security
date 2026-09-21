package is.fm.util;

public class EscaperURLPath implements Escaper {

    public static final String SPECIAL_CHARS = " %$&+,/:;=?@<>#%";

    private static final String[] encodeTable = new String[128];

    static {
        for (char ch = 0; ch < 128; ch++) {
            if (SPECIAL_CHARS.indexOf(ch) >= 0) {
                encodeTable[ch] = "%" + toHex(ch / 16) + toHex(ch % 16);
            }
        }
    }

    private static char toHex(int ch) {
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }

    private static void appendUtf8Byte(StringBuilder resultStr, int b) {
        resultStr.append('%').append(toHex((b >> 4) & 0xF)).append(toHex(b & 0xF));
    }

    private static void appendUtf8(StringBuilder resultStr, int codePoint) {
        if (codePoint < 0x80) {
            appendUtf8Byte(resultStr, codePoint);
        } else if (codePoint < 0x800) {
            appendUtf8Byte(resultStr, 0xC0 | (codePoint >> 6));
            appendUtf8Byte(resultStr, 0x80 | (codePoint & 0x3F));
        } else if (codePoint < 0x10000) {
            appendUtf8Byte(resultStr, 0xE0 | (codePoint >> 12));
            appendUtf8Byte(resultStr, 0x80 | ((codePoint >> 6) & 0x3F));
            appendUtf8Byte(resultStr, 0x80 | (codePoint & 0x3F));
        } else {
            appendUtf8Byte(resultStr, 0xF0 | (codePoint >> 18));
            appendUtf8Byte(resultStr, 0x80 | ((codePoint >> 12) & 0x3F));
            appendUtf8Byte(resultStr, 0x80 | ((codePoint >> 6) & 0x3F));
            appendUtf8Byte(resultStr, 0x80 | (codePoint & 0x3F));
        }
    }

    @Override
    public String escape(String input) {
//        return URLEncoder.encode(arg, StandardCharsets.UTF_8); // this encored uses "+" for spaces
        final String[] encodeTableCopy = encodeTable;
        final int inputLength = input.length();
        int firstEscapeIndex = 0;
        while (firstEscapeIndex < inputLength) {
            char ch = input.charAt(firstEscapeIndex);
            if (ch >= 128 || encodeTableCopy[ch] != null) {
                break;
            }
            firstEscapeIndex++;
        }
        if (firstEscapeIndex == inputLength) {
            return input;
        }

        final StringBuilder resultStr = new StringBuilder(3 * inputLength / 2);
        resultStr.append(input, 0, firstEscapeIndex);
        for (int i = firstEscapeIndex; i < inputLength; i++) {
            char ch = input.charAt(i);
            if (ch >= 128) {
                if (Character.isHighSurrogate(ch) && i + 1 < inputLength
                        && Character.isLowSurrogate(input.charAt(i + 1))) {
                    appendUtf8(resultStr, Character.toCodePoint(ch, input.charAt(i + 1)));
                    i++;
                } else {
                    appendUtf8(resultStr, ch);
                }
            } else {
                String encodedCh = encodeTableCopy[ch];
                if (encodedCh != null) {
                    resultStr.append(encodedCh);
                } else {
                    resultStr.append(ch);
                }
            }
        }
        return resultStr.toString();
    }
}
