package eu.virtusdevelops.ldmontage.repositories;

import eu.virtusdevelops.ldmontage.domain.work.BreakAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BreakAuditLogRepository extends JpaRepository<BreakAuditLog, Long> {

}
