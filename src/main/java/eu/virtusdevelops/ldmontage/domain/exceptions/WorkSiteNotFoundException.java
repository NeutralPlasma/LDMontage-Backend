package eu.virtusdevelops.ldmontage.domain.exceptions;

import org.aspectj.weaver.ast.Not;

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
