package removing.dependencies;

public class EscaperURLPath implements Escaper {

    public static final String SPECIAL_CHARS = " %$&+,/:;=?@<>#%";

    private final String[] encodeTable = new String[256];

    public EscaperURLPath() {
        initEncodeTable();
    }

    private void initEncodeTable() {
        for (char ch = 0; ch < 256; ch++) {
            if (ch > 128 || SPECIAL_CHARS.indexOf(ch) >= 0) {
                encodeTable[ch] = "%" + toHex(ch / 16) + toHex(ch % 16);
            }
        }
    }

    @Override
    public String escape(String input) {
//        return URLEncoder.encode(arg, StandardCharsets.UTF_8); // this encored uses "+" for spaces
        StringBuilder resultStr = new StringBuilder(input.length() + 4);
        char[] charArray = input.toCharArray();
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
            char ch = charArray[i];
            String encodedCh = encodeTable[ch];
            if (encodedCh != null) {
                resultStr.append(encodedCh);
            } else {
                resultStr.append(ch);
            }
        }
        return resultStr.toString();
    }

    private static char toHex(int ch) {
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }
}
