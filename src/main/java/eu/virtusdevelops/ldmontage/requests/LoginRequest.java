package eu.virtusdevelops.ldmontage.requests;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty
        String username,

        @NotEmpty
        String password
) {
}
