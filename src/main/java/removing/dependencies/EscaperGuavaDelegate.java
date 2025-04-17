package removing.dependencies;

public class EscaperGuavaDelegate implements Escaper {

    private final com.google.common.escape.Escaper delegate;

    public EscaperGuavaDelegate(com.google.common.escape.Escaper delegate) {
        this.delegate = delegate;
    }

    @Override
    public String escape(String arg) {
        return delegate.escape(arg);
    }
}
