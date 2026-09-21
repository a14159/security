package is.fm.util;

import java.util.Arrays;
import java.util.Locale;

public final class Base16 implements Encoder {

    /**
     * The Base16 alphabet according to Section 8 of RFC 4648.
     */
    private static final char[] BASE16_ALPHABET_UPPER = "0123456789ABCDEF".toCharArray();
    private static final char[] BASE16_ALPHABET_LOWER = "0123456789ABCDEF".toLowerCase(Locale.ROOT).toCharArray();
    private static final byte[] LOOKUP_UPPER = new byte[128];
    private static final byte[] LOOKUP_LOWER = new byte[128];
    static {
        Arrays.fill(LOOKUP_UPPER, (byte) 0xFF);
        Arrays.fill(LOOKUP_LOWER, (byte) 0xFF);
        for (int i = 0; i < BASE16_ALPHABET_UPPER.length; i++) {
            LOOKUP_UPPER[BASE16_ALPHABET_UPPER[i]] = (byte) i;
            LOOKUP_UPPER[BASE16_ALPHABET_LOWER[i]] = (byte) i;
            LOOKUP_LOWER[BASE16_ALPHABET_UPPER[i]] = (byte) i;
            LOOKUP_LOWER[BASE16_ALPHABET_LOWER[i]] = (byte) i;
        }
    }

    private final char[] alphabet;
    private final byte[] lookup;


    public Base16(boolean lowerCase) {
        if (lowerCase) {
            alphabet = BASE16_ALPHABET_LOWER;
            lookup = LOOKUP_LOWER;
        } else {
            alphabet = BASE16_ALPHABET_UPPER;
            lookup = LOOKUP_UPPER;
        }
    }

    public Encoder lowerCase() {
        return new Base16(true);
    }

    public Encoder omitPadding() {
        return this;
    }

    @Override
    public byte[] decode(String base16) {
        final byte[] lookup = this.lookup;
        char[] encoded = base16.toCharArray();
        final int size = encoded.length;
        if ((size & 1) != 0) {
            throw new IllegalArgumentException("Base16 input must have an even length: " + size);
        }
        byte[] decoded = new byte[size / 2];
        int baIdx = 0;
        for (int caIdx = 0; caIdx < size;) {
            int c0 = decodeChar(lookup, encoded[caIdx++]);
            int c1 = decodeChar(lookup, encoded[caIdx++]);
            decoded[baIdx++] = (byte) ((c0 << 4) | c1);
        }
        return decoded;
    }

    private static int decodeChar(byte[] lookup, char c) {
        if (c >= lookup.length) {
            throw new IllegalArgumentException("Illegal Base16 character: " + c);
        }
        int decoded = lookup[c] & 0xFF;
        if (decoded == 0xFF) {
            throw new IllegalArgumentException("Illegal Base16 character: " + c);
        }
        return decoded;
    }

    @Override
    public String encode(byte[] bytes) {
        final char[] alphabet = this.alphabet;
        final int size = bytes.length;
        char[] encoded = new char[size * 2];
        int caIdx = 0;
        for (int baIdx = 0; baIdx < size; baIdx++) {
            encoded[caIdx++] = alphabet[((bytes[baIdx] >> 4) & 0x0F)];
            encoded[caIdx++] = alphabet[((bytes[baIdx]) & 0x0F)];
        }
        return new String(encoded);
    }
}
