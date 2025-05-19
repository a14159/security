package is.fm.util;

public class Base64 implements Encoder {

    private final java.util.Base64.Decoder delegateDecoder;
    private final java.util.Base64.Encoder delegateEncoder;

    private final boolean base64URL;
    private final boolean padding;

    public Base64(boolean base64URL, boolean padding) {
        this.base64URL = base64URL;
        this.padding = padding;
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
        if (this.padding)
            return new Base64(this.base64URL, false);

        return this;
    }

    public String encode(byte[] base64) {
        return new String(delegateEncoder.encode(base64));
    }

    public byte[] decode(String base64) {
        return delegateDecoder.decode(base64);
    }
}
