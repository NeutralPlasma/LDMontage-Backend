package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.work.Work;
import eu.virtusdevelops.ldmontage.requests.WorkCreateRequest;

public interface WorkService {

    /**
     * Gets work by its id
     * @param id of the work
     * @return Work
     */
    Work getWork(long id);

    /**
     * Creates new work based on the data passed by request
     * @param request data for the new work
     * @return newly created work
     */
    Work createWork(WorkCreateRequest request);

    /**
     * Deletes existing work
     * @param id of the work you wish to delete
     */
    void deleteWork(long id);
}
