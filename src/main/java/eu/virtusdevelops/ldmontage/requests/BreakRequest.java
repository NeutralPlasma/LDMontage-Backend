package eu.virtusdevelops.ldmontage.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record BreakRequest(

        @Max(value = 180, message = "Longitude must be between -180 and 180 degrees!")
        @Min(value = -180, message = "Longitude must be between -180 and 180 degrees!")
        double longitude,

        @Max(value = 90, message = "Latitude must be between -90 and 90 degrees!")
        @Min(value = -90, message = "Latitude must be between -90 and 90 degrees!")
        double latitude
        // break type

) {
}
