package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.dto.BreakDTO;
import eu.virtusdevelops.ldmontage.requests.BreakEndRequest;
import eu.virtusdevelops.ldmontage.requests.BreakStartRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface WorkBreakService {

    /**
     * Creates a new break based on request
     * @param request parameters for new break
     * @return newly started break
     */
    Break startBreak(BreakStartRequest request);

    /**
     * Stops existing break (ends it)
     * @param breakId id of the break you wish to end
     * @param request request containing end location
     * @return ended break
     */
    Break stopBreak(Long breakId, BreakEndRequest request);

    /**
     * Deletes existing break
     * @param breakId id of the break you wish to delete
     */
    @PreAuthorize("@breakMiddleware.canDelete(breakId)")
    void deleteBreak(Long breakId);

    /**
     * Updates all the data of the existing break
     * @param breakId id of the break you wish to update
     * @param breakObj new data for the break
     * @return updated break
     */
    @PreAuthorize("@breakMiddleware.canUpdate(breakId)")
    Break updateBreak(long breakId, BreakDTO breakObj);

    /**
     * Partially update break
     * @param breakId if od the break you wish to patch
     * @param breakObj new data for the break
     * @return patched break
     */
    @PreAuthorize("@breakMiddleware.canUpdate(breakId)")
    Break patchBreak(long breakId, BreakDTO breakObj);

}
