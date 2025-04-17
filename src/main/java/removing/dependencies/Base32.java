package removing.dependencies;

import java.util.Arrays;

public final class Base32 implements Encoder {

    private static final String BASE32_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

    private final char[] ALPHABET;
    private final int[] LOOKUP = new int[256];

    private final boolean padding;
    private final boolean lowerCase;

    public Base32(boolean lowerCase, boolean padding) {
        this.lowerCase = lowerCase;
        if (lowerCase) {
            ALPHABET = BASE32_ALPHABET.toLowerCase().toCharArray();
        } else {
            ALPHABET = BASE32_ALPHABET.toCharArray();
        }
        this.padding = padding;
        initLookup();
    }

    public Encoder lowerCase() {
        return new Base32(true, this.padding);
    }

    public Encoder omitPadding() {
        return new Base32(this.lowerCase, false);
    }

    private void initLookup() {
        Arrays.fill(LOOKUP, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            LOOKUP[ALPHABET[i]] = i;
        }
    }

    public String encode(byte[] data) {
        StringBuilder encoded = new StringBuilder((data.length * 8 + 4) / 5);
        int buffer = 0, bitsLeft = 0;
        for (int i = 0, dataLength = data.length; i < dataLength; i++) {
            byte b = data[i];
            buffer <<= 8;
            buffer |= (b & 0xFF);
            bitsLeft += 8;
            while (bitsLeft >= 5) {
                encoded.append(ALPHABET[(buffer >> (bitsLeft - 5)) & 0x1F]);
                bitsLeft -= 5;
            }
        }
        if (bitsLeft > 0) {
            encoded.append(ALPHABET[(buffer << (5 - bitsLeft)) & 0x1F]);
        }
        if (padding) {
            while (encoded.length() % 8 != 0) {
                encoded.append('=');
            }
        }
        return encoded.toString();
    }

    public byte[] decode(String base32) {
        int paddingCount = 0;
        for (int i = base32.length() - 1; base32.charAt(i) == '='; i--) {
            paddingCount++;
        }
        byte[] decoded = new byte[(base32.length()- paddingCount) * 5 / 8];
        int buffer = 0, bitsLeft = 0, index = 0;
        for (char c : base32.toCharArray()) {
            if (c == '=') break;
            buffer <<= 5;
            buffer |= LOOKUP[c];
            bitsLeft += 5;
            if (bitsLeft >= 8) {
                decoded[index++] = (byte) ((buffer >> (bitsLeft - 8)) & 0xFF);
                bitsLeft -= 8;
            }
        }
        return decoded;
    }
}
