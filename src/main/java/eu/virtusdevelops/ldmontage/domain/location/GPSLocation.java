package eu.virtusdevelops.ldmontage.domain.location;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GPSLocation {

    private double latitude;

    private double longitude;

    private Date recordedTime;

}