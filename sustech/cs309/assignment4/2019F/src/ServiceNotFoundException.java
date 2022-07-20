import java.io.Serial;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
        super("");
    }

    @Serial
    private static final long serialVersionUID = 4070115954587235639L;
}
