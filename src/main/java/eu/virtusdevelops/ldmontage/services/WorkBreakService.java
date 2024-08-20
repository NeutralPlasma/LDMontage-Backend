package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.exceptions.BreakAlreadyEndedException;
import eu.virtusdevelops.ldmontage.domain.exceptions.BreakNotFoundException;
import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import eu.virtusdevelops.ldmontage.repositories.BreakRepository;
import eu.virtusdevelops.ldmontage.requests.BreakEndRequest;
import eu.virtusdevelops.ldmontage.requests.BreakStartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class WorkBreakService {
    private final BreakRepository breakRepository;


    public Break startBreak(BreakStartRequest request){
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
     * @param breakId id of the break to end
     * @param request request details (where break ended)
     * @return break object
     */
    public Break stopBreak(Long breakId, BreakEndRequest request){
        var breakOpt = breakRepository.findById(breakId);
        if (breakOpt.isEmpty()) {
            throw new BreakNotFoundException(breakId);
        }

        var breakObj = breakOpt.get();
        if(breakObj.getEndTime() != null) {
            throw new BreakAlreadyEndedException(breakId);
        }

        // TODO: update locations (are in the BreakEndRequest)
        breakObj.setEndTime(new Date());


        breakObj = breakRepository.save(breakObj);
        return breakObj;

    }

    /**
     * Deletes the break if it exists,
     * throws exceptions if not found
     * @param breakId id of the break to delete
     */
    @PreAuthorize("@breakMiddleware.canDelete(breakId)")
    public void deleteBreak(Long breakId){
        var breakOpt = breakRepository.findById(breakId);
        if (breakOpt.isEmpty()) {
            throw new BreakNotFoundException(breakId);
        }


        breakRepository.delete(breakOpt.get());
    }

    /**
     * Updates break with new information
     * @param breakObj updated object
     * @return updated break
     */
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    public Break updateBreak(long breakId, BreakDTO breakObj){
        // get original, compare and update


        return null; //breakRepository.save(breakObj);
    }

    /**
     * TODO!
     * Partialy updates data (not all data is changed)
     * @param breakObj n
     * @return updated break
     */
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    public Break patchBreak(long breakId, BreakDTO breakObj){

        return null; //breakRepository.save(breakObj);
    }

}
