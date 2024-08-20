package eu.virtusdevelops.ldmontage.domain.exceptions;

public class BreakAlreadyEndedException extends RuntimeException {
    public BreakAlreadyEndedException(long breakId) {
        super("Break already ended: " + breakId);
    }
}
