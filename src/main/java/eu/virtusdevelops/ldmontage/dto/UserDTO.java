package eu.virtusdevelops.ldmontage.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String phone,
        Date birthDate,
        boolean verified,
        Date createdAt,
        Date updatedAt,
        List<String> roles
) {
}
