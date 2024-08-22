package eu.virtusdevelops.ldmontage.domain.exceptions;

public class WorkNotFoundException extends NotFoundException {
    private final long workId;
    public WorkNotFoundException(long workId) {
        super("Work with id " + workId + " not found");
        this.workId = workId;
    }

    public long getWorkId() {
        return workId;
    }
}
