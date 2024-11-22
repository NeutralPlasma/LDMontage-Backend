package eu.virtusdevelops.ldmontage.domain.exceptions;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {
    private final UUID userId;

    public UserNotFoundException(UUID userId) {
        super("User with id " + userId + " not found");
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
