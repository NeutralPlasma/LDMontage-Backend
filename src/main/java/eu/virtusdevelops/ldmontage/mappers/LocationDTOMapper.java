package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.location.GPSLocation;
import eu.virtusdevelops.ldmontage.dto.LocationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class LocationDTOMapper implements Function<GPSLocation, LocationDTO> {

    @Override
    public LocationDTO apply(GPSLocation gpsLocation) {
        return new LocationDTO(
                gpsLocation.getLatitude(),
                gpsLocation.getLongitude()
        );
    }
}
