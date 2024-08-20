package eu.virtusdevelops.ldmontage.dto;

import java.util.Date;
import java.util.List;

public record WorkDTO(
        long id,
        String title,
        List<WorkSiteDTO> sites,
        Date createdAt,
        Date updatedAt
) {
}
