package eu.virtusdevelops.ldmontage.requests;

import java.util.Date;

public record WorkTimeEndRequest(
        Date time,
        double longitude,
        double latitiude
) {
}
