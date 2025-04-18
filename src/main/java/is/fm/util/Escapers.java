package is.fm.util;

public class Escapers {

    private static final Escaper JDK_ESCAPER = new EscaperURLPath();

    public static Escaper nullEscaper() {
        return Escaper.NULL;
    }

    public static Escaper urlPathSegmentEscaper() {
        return JDK_ESCAPER;
    }
}
