package is.fm.util;

public class Escapers {

    public static Escaper nullEscaper() {
        return Escaper.NULL;
    }

    public static Escaper urlPathSegmentEscaper() {
        return new EscaperURLPath();
    }
}
