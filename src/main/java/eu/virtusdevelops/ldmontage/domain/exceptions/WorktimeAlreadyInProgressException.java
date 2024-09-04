package eu.virtusdevelops.ldmontage.domain.exceptions;

import java.util.UUID;

public class WorktimeAlreadyInProgressException extends RuntimeException {
    private final UUID userId;


    public WorktimeAlreadyInProgressException(UUID userId) {
        super("User " + userId + " is already working");
        this.userId = userId;
    }
}
