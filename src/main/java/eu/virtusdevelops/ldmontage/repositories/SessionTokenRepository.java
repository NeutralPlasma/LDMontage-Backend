package eu.virtusdevelops.ldmontage.repositories;

import eu.virtusdevelops.ldmontage.domain.token.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
}
