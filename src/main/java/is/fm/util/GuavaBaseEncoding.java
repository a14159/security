package is.fm.util;


public class GuavaBaseEncoding implements Encoder {

    private final com.google.common.io.BaseEncoding delegate;

    public GuavaBaseEncoding(com.google.common.io.BaseEncoding delegate) {
        this.delegate = delegate;
    }

    public GuavaBaseEncoding lowerCase() {
        return new GuavaBaseEncoding(this.delegate.lowerCase());
    }

    public GuavaBaseEncoding omitPadding() {
        return new GuavaBaseEncoding(this.delegate.omitPadding());
    }

    public static GuavaBaseEncoding base16() {
        return new GuavaBaseEncoding(com.google.common.io.BaseEncoding.base16());
    }

    public static GuavaBaseEncoding base32() {
        return new GuavaBaseEncoding(com.google.common.io.BaseEncoding.base32());
    }

    public static GuavaBaseEncoding base32Hex() {
        return new GuavaBaseEncoding(com.google.common.io.BaseEncoding.base32Hex());
    }

    public static GuavaBaseEncoding base64() {
        return new GuavaBaseEncoding(com.google.common.io.BaseEncoding.base64());
    }

    public static GuavaBaseEncoding base64Url() {
        return new GuavaBaseEncoding(com.google.common.io.BaseEncoding.base64Url());
    }

    public String encode(byte[] arg) {
        return delegate.encode(arg);
    }

    public byte[] decode(String encoded) {
        return delegate.decode(encoded);
    }
}
