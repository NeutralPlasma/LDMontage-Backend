package eu.virtusdevelops.ldmontage.dto;

import java.util.Date;

public record WorkTimeAuditLogDTO(
        long id,
        String filedName,
        String oldValue,
        String newValue,
        UserDTO modifiedBy,
        Date createdAt
) {
}
