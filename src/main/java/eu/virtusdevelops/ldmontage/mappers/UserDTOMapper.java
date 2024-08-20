package eu.virtusdevelops.ldmontage.mappers;

import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(

        );
    }
}
