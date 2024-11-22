package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.user.TemporaryUserFile;
import eu.virtusdevelops.ldmontage.domain.user.User;

import java.util.Date;

public interface UserWorkService {

    /**
     * Creates pdf with exported work times for user
     * within specified dates
     *
     * @param user  the user for which to get work time
     * @param start from when
     * @param end   to when
     * @return temporary file which can then be served to the user
     */
    TemporaryUserFile exportUsersWork(User user, Date start, Date end);

}
