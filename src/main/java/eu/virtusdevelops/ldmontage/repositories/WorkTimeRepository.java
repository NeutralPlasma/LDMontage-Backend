package eu.virtusdevelops.ldmontage.repositories;

import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.domain.work.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {


    @Query("""
            SELECT wt FROM WorkTime wt
            WHERE wt.user = :user
            AND wt.endTime IS NOT NULL
            """)
    Optional<WorkTime> findUserActiveWorks(User user);
}
