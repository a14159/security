package removing.dependencies;

import java.util.Arrays;

public final class Base32Hex implements Encoder {

    private static final String BASE32_HEX_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUV";

    private final char[] ALPHABET;
    private final int[] LOOKUP = new int[128];

    private final boolean padding;
    private final boolean lowerCase;

    public Base32Hex(boolean lowerCase, boolean padding) {
        this.lowerCase = lowerCase;
        if (lowerCase) {
            ALPHABET = BASE32_HEX_ALPHABET.toLowerCase().toCharArray();
        } else {
            ALPHABET = BASE32_HEX_ALPHABET.toCharArray();
        }
        initLookup();
        this.padding = padding;
    }

    public Encoder lowerCase() {
        return new Base32Hex(true, this.padding);
    }

    public Encoder omitPadding() {
        return new Base32Hex(this.lowerCase, false);
    }

    private void initLookup() {
        Arrays.fill(LOOKUP, -1);
        for (int i = 0; i < BASE32_HEX_ALPHABET.length(); i++) {
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

    public byte[] decode(String base32Hex) {
        int paddingCount = 0;
        if (padding) {
            for (int i = base32Hex.length() - 1; i >= 0 && base32Hex.charAt(i) == '='; i--) {
                paddingCount++;
            }
        }
        int buffer = 0, bitsLeft = 0, idx = 0;
        byte[] decoded = new byte[(base32Hex.length() - paddingCount) * 5 / 8];
        char[] charArray = base32Hex.toCharArray();
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
            char c = charArray[i];
            if (c == '=') {
                break;
            }
            if (LOOKUP[c] == -1) {
                throw new IllegalArgumentException("Invalid character in Base32 Hex encoded string.");
            }
            buffer <<= 5;
            buffer |= LOOKUP[c];
            bitsLeft += 5;
            if (bitsLeft >= 8) {
                decoded[idx++] = (byte) ((buffer >> (bitsLeft - 8)) & 0xFF);
                bitsLeft -= 8;
            }
        }
        return decoded;
    }
}
