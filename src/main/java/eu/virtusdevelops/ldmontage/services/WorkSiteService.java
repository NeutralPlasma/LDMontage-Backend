package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.exceptions.UserAlreadyAuthorizedOnWorksiteException;
import eu.virtusdevelops.ldmontage.domain.exceptions.UserNotAuthorizedOnWorksite;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorkNotFoundException;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorkSiteNotFoundException;
import eu.virtusdevelops.ldmontage.domain.work.WorkSite;
import eu.virtusdevelops.ldmontage.requests.WorkSiteCreateRequest;

import java.util.UUID;

public interface WorkSiteService {

    /**
     * Creates new work site based on request
     *
     * @param request data for new work site
     * @return newly created worksite
     */
    WorkSite createNew(WorkSiteCreateRequest request)
            throws WorkNotFoundException;

    /**
     * Authorizes user to work at specific worksite
     *
     * @param worksiteId worksite
     * @param userId     user/worker
     * @return updated worksite
     */
    WorkSite addAuthorizedWorker(long worksiteId, UUID userId)
            throws WorkSiteNotFoundException,
            UserAlreadyAuthorizedOnWorksiteException;

    /**
     * Removes authorized user from worksite
     *
     * @param worksiteId worksite
     * @param workerId   user/worker
     * @return updated worksite
     */
    WorkSite removeAuthorizedWorker(long worksiteId, UUID workerId)
            throws WorkSiteNotFoundException,
            UserNotAuthorizedOnWorksite;

    /**
     * Deletes existing worksite
     *
     * @param worksiteId the worksite you wish to delete
     */
    void deleteWorkSite(long worksiteId)
            throws WorkSiteNotFoundException;
}
