package eu.virtusdevelops.ldmontage.domain.exceptions;

public class WorkSiteNotFoundException extends NotFoundException {
    private final long workSiteId;

    public WorkSiteNotFoundException(long workSiteId) {
        super("Worksite with id " + workSiteId + " not found");
        this.workSiteId = workSiteId;
    }

    public long getWorkSiteId() {
        return workSiteId;
    }
}
