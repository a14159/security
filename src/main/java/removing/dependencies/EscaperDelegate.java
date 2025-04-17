package removing.dependencies;

public class EscaperDelegate implements Escaper {

    private final com.google.common.escape.Escaper delegate;

    public EscaperDelegate(com.google.common.escape.Escaper delegate) {
        this.delegate = delegate;
    }

    @Override
    public String escape(String arg) {
        return delegate.escape(arg);
    }
}
