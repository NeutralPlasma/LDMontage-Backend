package eu.virtusdevelops.ldmontage.domain.exceptions;

import lombok.Getter;

@Getter
public class BreakAlreadyEndedException extends RuntimeException {
    private final long breakId;

    public BreakAlreadyEndedException(long breakId) {
        super("Break already ended: " + breakId);
        this.breakId = breakId;
    }
}
