package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.work.WorkSite;
import eu.virtusdevelops.ldmontage.dto.WorkSiteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class WorkSiteDTOMapper implements Function<WorkSite, WorkSiteDTO> {


    @Override
    public WorkSiteDTO apply(WorkSite site) {
        return new WorkSiteDTO(
                site.getId(),
                site.getTitle(),
                site.getDescription(),
                site.getLatitude(),
                site.getLongitude()
        );
    }
}
