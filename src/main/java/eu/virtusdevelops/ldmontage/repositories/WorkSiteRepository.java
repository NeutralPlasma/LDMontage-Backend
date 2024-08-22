package eu.virtusdevelops.ldmontage.repositories;

import eu.virtusdevelops.ldmontage.domain.work.Work;
import eu.virtusdevelops.ldmontage.domain.work.WorkSite;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkSiteRepository extends JpaRepository<WorkSite, Long> {

}
