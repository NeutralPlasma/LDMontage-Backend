package eu.virtusdevelops.ldmontage.controllers.admin;

import eu.virtusdevelops.ldmontage.domain.exceptions.UserNotFoundException;
import eu.virtusdevelops.ldmontage.dto.UserDTO;
import eu.virtusdevelops.ldmontage.mappers.UserDTOMapper;
import eu.virtusdevelops.ldmontage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserRepository userRepository;

    private final UserDTOMapper userDTOMapper;


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(
            @PathVariable UUID id
    ) {
        var userOpt = userRepository.findById(id);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(id);

        // maybe return userdto with admin information ?
        return ResponseEntity.ok(userDTOMapper.apply(userOpt.get()));
    }


    @PostMapping("/")
    public ResponseEntity<UserDTO> create() {


        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable UUID id
    ) {

    }

    @PutMapping("/{id}")
    public void update() {

    }

    @PostMapping("/{id}/disable")
    public ResponseEntity<UserDTO> disable(
            @PathVariable UUID id
    ) {
        var userOpt = userRepository.findById(id);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(id);

        var user = userOpt.get();
        user.setLocked(true);
        user = userRepository.save(user);
        return ResponseEntity.ok(userDTOMapper.apply(user));
    }

    @PatchMapping("/{id}")
    public void patch() {

    }
}
