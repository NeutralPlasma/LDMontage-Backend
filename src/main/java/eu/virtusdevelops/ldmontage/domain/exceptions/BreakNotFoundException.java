package eu.virtusdevelops.ldmontage.domain.exceptions;

import lombok.Getter;

@Getter
public class BreakNotFoundException extends RuntimeException {
    private final long breakId;
    public BreakNotFoundException(long breakId) {
        super("Break with id " + breakId + " not found");
        this.breakId = breakId;
    }

}
