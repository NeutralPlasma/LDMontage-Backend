package eu.virtusdevelops.ldmontage.repositories;

import eu.virtusdevelops.ldmontage.domain.work.WorkTimeAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkTimeAuditLogRepository extends JpaRepository<WorkTimeAuditLog, Long> {

}
