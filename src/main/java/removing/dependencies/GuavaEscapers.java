package removing.dependencies;

public class GuavaEscapers {

    public static Escaper nullEscaper() {
        return Escaper.NULL;
    }

    public static Escaper urlPathSegmentEscaper() {
        return new EscaperDelegate(com.google.common.net.UrlEscapers.urlPathSegmentEscaper());
    }
}
