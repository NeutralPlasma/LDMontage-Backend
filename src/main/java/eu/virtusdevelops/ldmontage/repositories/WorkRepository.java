package eu.virtusdevelops.ldmontage.repositories;

import eu.virtusdevelops.ldmontage.domain.work.Work;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkRepository extends JpaRepository<Work, Long> {

}
