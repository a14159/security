package removing.dependencies;


public class BaseEncoding {

    public static Encoder base16() {
        return new Base16(false);
    }

    public static Encoder base32() {
        return new Base32(false, true);
    }

    public static Encoder base32Hex() {
        return new Base32Hex(false, true);
    }

    public static Encoder base64() {
        return new Base64(false, true);
    }

    public static Encoder base64Url() {
        return new Base64(true, true);
    }
}
