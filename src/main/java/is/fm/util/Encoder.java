package is.fm.util;

public interface Encoder {

    Encoder lowerCase();

    Encoder omitPadding();

    byte[] decode(String base16);

    String encode(byte[] bytes);
}
