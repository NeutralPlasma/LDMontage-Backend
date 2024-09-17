package eu.virtusdevelops.ldmontage.domain.exceptions;

public class WorkTimeNotFound extends RuntimeException {
    public WorkTimeNotFound(long id) {
        super("Work time with id " + id + " not found");
    }
}
