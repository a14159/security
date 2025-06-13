package is.fm.util;

import java.util.Arrays;

public final class Base16 implements Encoder {

    /**
     * The Base16 alphabet according to Section 8 of RFC 4648.
     */
    private static final String BASE16_ALPHABET = "0123456789ABCDEF";

    private final char[] ALPHABET;
    private final byte[] LOOKUP = new byte[128];


    public Base16(boolean lowerCase) {
        if (lowerCase) {
            ALPHABET = BASE16_ALPHABET.toLowerCase().toCharArray();
        } else {
            ALPHABET = BASE16_ALPHABET.toCharArray();
        }
        initLookup();
    }

    public Encoder lowerCase() {
        return new Base16(true);
    }

    public Encoder omitPadding() {
        return this;
    }

    private void initLookup() {
        Arrays.fill(LOOKUP, (byte) 0xFF);
        for (int i = 0; i < ALPHABET.length; i++) {
            LOOKUP[ALPHABET[i]] = (byte) i;
        }
    }

    @Override
    public byte[] decode(String base16) {
        final byte[] lookup = LOOKUP;
        char[] encoded = base16.toCharArray();
        final int size = encoded.length;
        byte[] decoded = new byte[size / 2];
        int baIdx = 0;
        for (int caIdx = 0; caIdx < size;) {
            byte c0 = lookup[encoded[caIdx++]];
            byte c1 = lookup[encoded[caIdx++]];
            decoded[baIdx++] = (byte) ((c0 << 4) | c1);
        }
        return decoded;
    }

    @Override
    public String encode(byte[] bytes) {
        final char[] alphabet = ALPHABET;
        final int size = bytes.length;
        byte[] encoded = new byte[size * 2];
        int caIdx = 0;
        for (int baIdx = 0; baIdx < size; baIdx++) {
            encoded[caIdx++] = (byte) alphabet[((bytes[baIdx] >> 4) & 0x0F)];
            encoded[caIdx++] = (byte) alphabet[((bytes[baIdx]) & 0x0F)];
        }
        return new String(encoded);
    }
}
