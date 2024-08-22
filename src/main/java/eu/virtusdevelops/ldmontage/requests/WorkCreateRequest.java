package eu.virtusdevelops.ldmontage.requests;

import jakarta.validation.constraints.NotEmpty;

public record WorkCreateRequest(
        @NotEmpty(message = "Work title can not be empty")
        String title
) {
}
