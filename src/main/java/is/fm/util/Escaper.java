package is.fm.util;

import java.util.Objects;

public interface Escaper {
    Escaper NULL = arg -> {
        Objects.requireNonNull(arg);
        return arg;
    };
    String escape(String arg);
}
