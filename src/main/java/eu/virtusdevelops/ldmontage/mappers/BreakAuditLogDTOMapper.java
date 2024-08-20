package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.domain.work.BreakAuditLog;
import eu.virtusdevelops.ldmontage.dto.BreakAuditLogDTO;
import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@RequiredArgsConstructor
@Component
public class BreakAuditLogDTOMapper implements Function<BreakAuditLog, BreakAuditLogDTO> {
    private final UserDTOMapper userDTOMapper;

    @Override
    public BreakAuditLogDTO apply(BreakAuditLog auditLog) {
        return new BreakAuditLogDTO(
            auditLog.getId(),
                auditLog.getFieldName(),
                auditLog.getOldValue(),
                auditLog.getNewValue(),
                userDTOMapper.apply(auditLog.getModifiedBy()),
                auditLog.getCreatedAt()
        );
    }
}
