package is.fm.util;

public class GuavaEscapers {

    public static Escaper nullEscaper() {
        return Escaper.NULL;
    }

    public static Escaper urlPathSegmentEscaper() {
        return new EscaperGuavaDelegate(com.google.common.net.UrlEscapers.urlPathSegmentEscaper());
    }
}
