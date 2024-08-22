package eu.virtusdevelops.ldmontage.domain.exceptions;

import java.util.UUID;

public class UserNotAuthorizedOnWorksite extends RuntimeException {
    private final UUID userId;
    private final long worksiteId;

    public UserNotAuthorizedOnWorksite(UUID userId, long worksiteId) {
        super("User with id " + userId + " not authorized on worksite " + worksiteId);
        this.userId = userId;
        this.worksiteId = worksiteId;
    }

    public UUID getUserId() {
        return userId;
    }

    public long getWorksiteId() {
        return worksiteId;
    }
}
