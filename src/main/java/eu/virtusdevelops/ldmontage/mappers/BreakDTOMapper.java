package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class BreakDTOMapper implements Function<Break, BreakDTO> {
    private final BreakAuditLogDTOMapper breakAuditLogDTOMapper;


    @Override
    public BreakDTO apply(Break breakObj) {
        return new BreakDTO(
                breakObj.getId(),
                breakObj.getStartTime(),
                breakObj.getEndTime(),
                breakObj.getAuditLog().stream().map(breakAuditLogDTOMapper).toList(),
                breakObj.getCreatedAt(),
                breakObj.getUpdatedAt()
        );
    }
}
