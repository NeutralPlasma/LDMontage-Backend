package eu.virtusdevelops.ldmontage.dto;


import java.util.Date;
import java.util.List;

public record WorkTimeDTO(
        long id,
        Date startTime,
        Date endTime,
        LocationDTO startLocation,
        LocationDTO endLocation,
        List<BreakDTO> breaks,
        WorkSiteDTO worksite,
        List<WorkTimeAuditLogDTO> auditLog
) {
}
