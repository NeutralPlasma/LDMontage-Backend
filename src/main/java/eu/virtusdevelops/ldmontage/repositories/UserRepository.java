package eu.virtusdevelops.ldmontage.repositories;

import eu.virtusdevelops.ldmontage.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds user by its email
     * @param email users email
     * @return user if found or empty optional
     */
    Optional<User> findByEmail(String email);
}
