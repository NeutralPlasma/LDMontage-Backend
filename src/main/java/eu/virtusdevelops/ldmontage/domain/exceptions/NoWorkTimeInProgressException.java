package eu.virtusdevelops.ldmontage.domain.exceptions;

public class NoWorkTimeInProgressException extends RuntimeException {
    public NoWorkTimeInProgressException() {
        super("There's currently no worktime in progress.");
    }
}
