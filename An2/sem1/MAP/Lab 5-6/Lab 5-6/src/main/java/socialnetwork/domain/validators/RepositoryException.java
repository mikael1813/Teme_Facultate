package socialnetwork.domain.validators;

/**
 * Exceptie ce se arunca la inexistenta datelor in repository
 */
public class RepositoryException extends RuntimeException {
    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }
}
