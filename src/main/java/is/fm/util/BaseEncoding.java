package is.fm.util;


public final class BaseEncoding {

    private static final Encoder BASE16 = new Base16(false);
    private static final Encoder BASE32 = new Base32(false, true);
    private static final Encoder BASE32_HEX = new Base32Hex(false, true);
    private static final Encoder BASE64 = new Base64(false, true);
    private static final Encoder BASE64_URL = new Base64(true, true);

    private BaseEncoding() {
    }

    public static Encoder base16() {
        return BASE16;
    }

    public static Encoder base32() {
        return BASE32;
    }

    public static Encoder base32Hex() {
        return BASE32_HEX;
    }

    public static Encoder base64() {
        return BASE64;
    }

    public static Encoder base64Url() {
        return BASE64_URL;
    }
}
