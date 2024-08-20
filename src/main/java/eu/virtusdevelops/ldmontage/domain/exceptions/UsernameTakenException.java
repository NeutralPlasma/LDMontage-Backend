package eu.virtusdevelops.ldmontage.domain.exceptions;

import lombok.Getter;

@Getter
public class UsernameTakenException extends RuntimeException {
    private final String username;
    public UsernameTakenException(String username) {
        super("Username " + username + " is already taken");
        this.username = username;
    }
}
