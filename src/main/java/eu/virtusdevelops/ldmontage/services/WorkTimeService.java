package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.exceptions.NoWorkTimeInProgressException;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorkSiteNotFoundException;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorkTimeNotFound;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorktimeAlreadyInProgressException;
import eu.virtusdevelops.ldmontage.domain.work.WorkTime;
import eu.virtusdevelops.ldmontage.dto.WorkTimeDTO;
import eu.virtusdevelops.ldmontage.requests.WorkTimeEndRequest;
import eu.virtusdevelops.ldmontage.requests.WorkTimeStartRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface WorkTimeService {

    /**
     * Starts new work for the user
     *
     * @param request data
     * @return new work
     */
    @PreAuthorize("@workMiddleware.canWorkAtLocation(request.worksiteId())")
    WorkTime startWork(WorkTimeStartRequest request)
            throws WorkSiteNotFoundException,
            WorktimeAlreadyInProgressException;

    /**
     * Ends existing work for the user
     *
     * @param request data
     * @return ended worktime
     */
    WorkTime endWorkTime(WorkTimeEndRequest request)
            throws NoWorkTimeInProgressException;


    /**
     * Deletes existing worktime
     *
     * @param id of the worktime
     */
    @PreAuthorize("hasAnyAuthority('WORKTIME_DELETE')")
    void deleteWorkTime(long id)
            throws WorkTimeNotFound;

    /**
     * Updates worktime based on data provided
     *
     * @param id          of the worktime
     * @param workTimeDTO new data
     * @return updated worktime
     */
    @PreAuthorize("hasAnyAuthority('WORKTIME_UPDATE')")
    WorkTime updateWorkTime(long id, WorkTimeDTO workTimeDTO);

    /**
     * Partially updates worktime based on data provided
     *
     * @param id          of the worktime
     * @param workTimeDTO
     * @return patched worktime
     */
    @PreAuthorize("hasAnyAuthority('WORKTIME_UPDATE')")
    WorkTime patchWorkTime(long id, WorkTimeDTO workTimeDTO);
}
