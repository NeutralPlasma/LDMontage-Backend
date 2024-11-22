package eu.virtusdevelops.ldmontage.requests;

import java.util.Date;

public record WorkTimeStartRequest(
        long worksiteId,
        Date time,
        double longitude,
        double latitude
) {
}
