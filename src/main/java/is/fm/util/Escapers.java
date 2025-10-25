package is.fm.util;

public class Escapers {

    private static final EscaperURLPath urlPathEscaper = new EscaperURLPath();

    public static Escaper nullEscaper() {
        return Escaper.NULL;
    }

    public static Escaper urlPathSegmentEscaper() {
        return urlPathEscaper;
    }
}
