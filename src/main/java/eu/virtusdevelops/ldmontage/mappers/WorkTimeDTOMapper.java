package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.work.WorkTime;
import eu.virtusdevelops.ldmontage.dto.WorkTimeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class WorkTimeDTOMapper implements Function<WorkTime, WorkTimeDTO> {
    private final WorkSiteDTOMapper workSiteDTOMapper;
    private final LocationDTOMapper locationDTOMapper;
    private final WorkTimeAuditLogDTOMapper workTimeAuditLogDTOMapper;
    private final BreakDTOMapper breakDTOMapper;

    @Override
    public WorkTimeDTO apply(WorkTime workTime) {
        return new WorkTimeDTO(
                workTime.getId(),
                workTime.getStartTime(),
                workTime.getEndTime(),
                locationDTOMapper.apply(workTime.getStartLocation()),
                locationDTOMapper.apply(workTime.getEndLocation()),
                workTime.getBreaks().stream().map(breakDTOMapper).toList(),
                workSiteDTOMapper.apply(workTime.getWorkSite()),
                workTime.getAuditLog().stream().map(workTimeAuditLogDTOMapper).toList()
        );
    }
}
