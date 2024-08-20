package eu.virtusdevelops.ldmontage.dto;

import java.util.Date;
import java.util.List;

public record BreakDTO(
        long id,
        Date startTime,
        Date endTime,
        // TODO: location

        List<BreakAuditLogDTO> auditLog,
        Date createdAt,
        Date updatedAt
)
{ }
