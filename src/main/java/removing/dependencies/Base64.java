package removing.dependencies;

public class Base64 implements Encoder {

    private final java.util.Base64.Decoder delegateDecoder;
    private final java.util.Base64.Encoder delegateEncoder;

    private final boolean base64URL;

    public Base64(boolean base64URL, boolean padding) {
        this.base64URL = base64URL;
        if (base64URL) {
            delegateDecoder = java.util.Base64.getUrlDecoder();
            if (padding)
                delegateEncoder = java.util.Base64.getUrlEncoder();
            else {
                delegateEncoder = java.util.Base64.getUrlEncoder().withoutPadding();
            }
        } else {
            delegateDecoder = java.util.Base64.getDecoder();
            delegateEncoder = java.util.Base64.getEncoder();
        }
    }

    public Encoder lowerCase() {
        return this;
    }

    public Encoder omitPadding() {
        return new Base32(this.base64URL, false);
    }

    public String encode(byte[] base64) {
        return new String(delegateEncoder.encode(base64));
    }

    public byte[] decode(String base64) {
        return delegateDecoder.decode(base64);
    }
}
