package is.fm.util;

import java.util.Arrays;

public final class Base32 implements Encoder {

    private static final char[] BASE32_ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
    private static final char[] BASE32_ALPHABET_LOWER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toLowerCase().toCharArray();

    private static final  int[] LOOKUP_UPPER = new int[256];
    private static final  int[] LOOKUP_LOWER = new int[256];
    static {
        Arrays.fill(LOOKUP_UPPER, -1);
        Arrays.fill(LOOKUP_LOWER, -1);
        for (int i = 0; i < BASE32_ALPHABET_UPPER.length; i++) {
            LOOKUP_UPPER[BASE32_ALPHABET_UPPER[i]] = i;
            LOOKUP_LOWER[BASE32_ALPHABET_LOWER[i]] = i;
        }
    }

    private final char[] alphabet;
    private final int[] lookup;

    private final boolean padding;
    private final boolean lowerCase;

    public Base32(boolean lowerCase, boolean padding) {
        this.lowerCase = lowerCase;
        if (lowerCase) {
            alphabet = BASE32_ALPHABET_LOWER;
            lookup = LOOKUP_LOWER;
        } else {
            alphabet = BASE32_ALPHABET_UPPER;
            lookup = LOOKUP_UPPER;
        }
        this.padding = padding;
    }

    public Encoder lowerCase() {
        return new Base32(true, this.padding);
    }

    public Encoder omitPadding() {
        return new Base32(this.lowerCase, false);
    }

    public String encode(byte[] data) {
        final char[] alphabet = this.alphabet;
        StringBuilder encoded = new StringBuilder((data.length * 8 + 4) / 5);
        int buffer = 0, bitsLeft = 0;
        for (int i = 0, dataLength = data.length; i < dataLength; i++) {
            byte b = data[i];
            buffer <<= 8;
            buffer |= (b & 0xFF);
            bitsLeft += 8;
            while (bitsLeft >= 5) {
                encoded.append(alphabet[(buffer >> (bitsLeft - 5)) & 0x1F]);
                bitsLeft -= 5;
            }
        }
        if (bitsLeft > 0) {
            encoded.append(alphabet[(buffer << (5 - bitsLeft)) & 0x1F]);
        }
        if (padding) {
            int currentLength = encoded.length();
            int toAdd = (8 - currentLength % 8) % 8;
            encoded.ensureCapacity(currentLength + toAdd);
            while (currentLength++ % 8 != 0) {
                encoded.append('=');
            }
        }
        return encoded.toString();
    }

    public byte[] decode(String base32) {
        final int[] lookup = this.lookup;
        int paddingCount = 0;
        for (int i = base32.length() - 1; base32.charAt(i) == '='; i--) {
            paddingCount++;
        }
        byte[] decoded = new byte[(base32.length()- paddingCount) * 5 / 8];
        int buffer = 0, bitsLeft = 0, index = 0;
        for (char c : base32.toCharArray()) {
            if (c == '=') break;
            buffer <<= 5;
            buffer |= lookup[c];
            bitsLeft += 5;
            if (bitsLeft >= 8) {
                decoded[index++] = (byte) ((buffer >> (bitsLeft - 8)) & 0xFF);
                bitsLeft -= 8;
            }
        }
        return decoded;
    }
}
