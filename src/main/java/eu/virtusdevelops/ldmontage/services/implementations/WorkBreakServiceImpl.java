package eu.virtusdevelops.ldmontage.services.implementations;

import eu.virtusdevelops.ldmontage.domain.exceptions.BreakAlreadyEndedException;
import eu.virtusdevelops.ldmontage.domain.exceptions.BreakNotFoundException;
import eu.virtusdevelops.ldmontage.domain.location.GPSLocation;
import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.domain.work.BreakAuditLog;
import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import eu.virtusdevelops.ldmontage.repositories.BreakAuditLogRepository;
import eu.virtusdevelops.ldmontage.repositories.BreakRepository;
import eu.virtusdevelops.ldmontage.requests.BreakEndRequest;
import eu.virtusdevelops.ldmontage.requests.BreakStartRequest;
import eu.virtusdevelops.ldmontage.services.WorkBreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class WorkBreakServiceImpl implements WorkBreakService {
    private final BreakRepository breakRepository;
    private final BreakAuditLogRepository breakAuditLogRepository;


    @Override
    public Break startBreak(BreakStartRequest request) {
        // TODO
        // - check if user is currently working
        // - get users active work
        // - check that user isn't already on break
        // - check locations
        // - create new break

        return Break.builder().build();
    }


    /**
     * Ends the existing break
     * throws error if break doesn't exist or is already ended
     *
     * @param breakId id of the break to end
     * @param request request details (where break ended)
     * @return break object
     */
    @Override
    public Break stopBreak(Long breakId, BreakEndRequest request) {
        var breakOpt = breakRepository.findById(breakId);
        if (breakOpt.isEmpty()) {
            throw new BreakNotFoundException(breakId);
        }

        var breakObj = breakOpt.get();
        if (breakObj.getEndTime() != null) {
            throw new BreakAlreadyEndedException(breakId);
        }

        breakObj.setEndTime(new Date());

        breakObj = breakRepository.save(breakObj);
        breakObj.setEndLocation(GPSLocation.builder()
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build());
        breakRepository.save(breakObj);

        return breakObj;

    }

    /**
     * Deletes the break if it exists,
     * throws exceptions if not found
     *
     * @param breakId id of the break to delete
     */
    @Override
    public void deleteBreak(Long breakId) {
        var breakOpt = breakRepository.findById(breakId);
        if (breakOpt.isEmpty()) {
            throw new BreakNotFoundException(breakId);
        }


        breakRepository.delete(breakOpt.get());
    }

    /**
     * Updates break with new information
     *
     * @param breakId  id of the existing break to update
     * @param breakObj updated object
     * @return updated break
     */
    @Override
    public Break updateBreak(long breakId, BreakDTO breakObj) {
        var breakOpt = breakRepository.findById(breakId);
        if (breakOpt.isEmpty()) {
            throw new BreakNotFoundException(breakId);
        }
        var oldBreakObj = breakOpt.get();


        breakAuditLogRepository.save(
                BreakAuditLog.builder()
                        .workBreak(oldBreakObj)
                        .fieldName("startTime")
                        .oldValue(oldBreakObj.getStartTime().toString())
                        .newValue(breakObj.startTime().toString())
                        .build()
        );

        breakAuditLogRepository.save(
                BreakAuditLog.builder()
                        .workBreak(oldBreakObj)
                        .fieldName("endTime")
                        .oldValue(oldBreakObj.getEndTime().toString())
                        .newValue(breakObj.endTime().toString())
                        .build()
        );

        oldBreakObj.setStartTime(breakObj.startTime());
        oldBreakObj.setEndTime(breakObj.endTime());

        oldBreakObj = breakRepository.save(oldBreakObj);

        return oldBreakObj; //breakRepository.save(breakObj);
    }

    /**
     * Partially updates data (not all data is changed)
     *
     * @param breakId  id for the existing break to patch
     * @param breakObj new data
     * @return updated break
     */
    @Override
    public Break patchBreak(long breakId, BreakDTO breakObj) {

        var breakOpt = breakRepository.findById(breakId);
        if (breakOpt.isEmpty()) {
            throw new BreakNotFoundException(breakId);
        }
        var oldBreakObj = breakOpt.get();

        if (breakObj.startTime() != null) {
            breakAuditLogRepository.save(
                    BreakAuditLog.builder()
                            .workBreak(oldBreakObj)
                            .fieldName("startTime")
                            .oldValue(oldBreakObj.getStartTime().toString())
                            .newValue(breakObj.startTime().toString())
                            .build()
            );
            oldBreakObj.setStartTime(breakObj.startTime());
        }

        if (breakObj.endTime() != null) {
            breakAuditLogRepository.save(
                    BreakAuditLog.builder()
                            .workBreak(oldBreakObj)
                            .fieldName("endTime")
                            .oldValue(oldBreakObj.getEndTime().toString())
                            .newValue(breakObj.endTime().toString())
                            .build()
            );
            oldBreakObj.setEndTime(breakObj.endTime());
        }

        // check start and end locations too

        oldBreakObj = breakRepository.save(oldBreakObj);
        return oldBreakObj;
    }

}
