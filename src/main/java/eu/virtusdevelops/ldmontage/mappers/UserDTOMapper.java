package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getBirthDate(),
            user.isVerified(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
    }
}
