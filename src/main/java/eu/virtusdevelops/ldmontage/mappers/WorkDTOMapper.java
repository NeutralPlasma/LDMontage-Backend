package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.domain.work.Work;
import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import eu.virtusdevelops.ldmontage.dto.WorkDTO;
import eu.virtusdevelops.ldmontage.dto.WorkSiteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class WorkDTOMapper implements Function<Work, WorkDTO> {
    private final WorkSiteDTOMapper workSiteDTOMapper;


    @Override
    public WorkDTO apply(Work work) {
        return new WorkDTO(
            work.getId(),
            work.getTitle(),
            work.getWorkSites().stream().map(workSiteDTOMapper).toList(),
            work.getCreatedAt(),
            work.getUpdatedAt()
        );
    }
}
