package eu.virtusdevelops.ldmontage.domain.exceptions;

import java.util.UUID;

public class UserAlreadyAuthorizedOnWorksiteException extends RuntimeException {
    private final UUID userId;
    private final long worksiteId;


    public UserAlreadyAuthorizedOnWorksiteException(UUID userId, long worksiteId) {
        super("User with id " + userId + " already authorized on worksite " + worksiteId);
        this.userId = userId;
        this.worksiteId = worksiteId;
    }
}
