package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.work.WorkTimeAuditLog;
import eu.virtusdevelops.ldmontage.dto.WorkTimeAuditLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@RequiredArgsConstructor
@Component
public class WorkTimeAuditLogDTOMapper implements Function<WorkTimeAuditLog, WorkTimeAuditLogDTO> {
    private final UserDTOMapper userDTOMapper;

    @Override
    public WorkTimeAuditLogDTO apply(WorkTimeAuditLog auditLog) {
        return new WorkTimeAuditLogDTO(
                auditLog.getId(),
                auditLog.getFieldName(),
                auditLog.getOldValue(),
                auditLog.getNewValue(),
                userDTOMapper.apply(auditLog.getModifiedBy()),
                auditLog.getCreatedAt()
        );
    }
}
