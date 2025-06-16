package is.fm.util;

public interface Encoder {

    Encoder lowerCase();

    Encoder omitPadding();

    byte[] decode(String s);

    String encode(byte[] bytes);
}
