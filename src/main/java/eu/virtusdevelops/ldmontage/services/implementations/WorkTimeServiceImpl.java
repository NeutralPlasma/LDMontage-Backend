package eu.virtusdevelops.ldmontage.services.implementations;


import eu.virtusdevelops.ldmontage.domain.exceptions.NoWorkTimeInProgressException;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorkSiteNotFoundException;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorkTimeNotFound;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorktimeAlreadyInProgressException;
import eu.virtusdevelops.ldmontage.domain.location.GPSLocation;
import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.domain.work.WorkTime;
import eu.virtusdevelops.ldmontage.dto.WorkTimeDTO;
import eu.virtusdevelops.ldmontage.repositories.UserRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkSiteRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkTimeRepository;
import eu.virtusdevelops.ldmontage.requests.WorkTimeEndRequest;
import eu.virtusdevelops.ldmontage.requests.WorkTimeStartRequest;
import eu.virtusdevelops.ldmontage.services.WorkTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class WorkTimeServiceImpl implements WorkTimeService {

    private final WorkTimeRepository workTimeRepository;
    private final UserRepository userRepository;
    private final WorkSiteRepository workSiteRepository;
    private final WorkRepository workRepository;


    @Override
    public WorkTime startWork(WorkTimeStartRequest request) {
        var user = getCurrentUser();

        var worksite = workSiteRepository.findById(request.worksiteId())
                .orElseThrow(() -> new WorkSiteNotFoundException(request.worksiteId()));

        if (workTimeRepository.findUserActiveWorks(user).isPresent()) {
            throw new WorktimeAlreadyInProgressException(user.getId());
        }

        validateTimeDifference(request.time());

        var location = createLocation(request.latitude(), request.longitude());

        var workTime = WorkTime.builder()
                .user(user)
                .startLocation(location)
                .workSite(worksite)
                .startTime(request.time())
                .build();

        return workTimeRepository.save(workTime);
    }

    @Override
    public WorkTime endWorkTime(WorkTimeEndRequest request) {
        var user = getCurrentUser();

        var currentWorkTime = workTimeRepository.findUserActiveWorks(user)
                .orElseThrow(NoWorkTimeInProgressException::new);

        validateTimeDifference(request.time());

        var location = createLocation(request.latitude(), request.longitude());

        currentWorkTime.setEndLocation(location);
        currentWorkTime.setEndTime(request.time());

        return workTimeRepository.save(currentWorkTime);
    }

    @Override
    public void deleteWorkTime(long id) {
        var workTime = workTimeRepository.findById(id)
                .orElseThrow(() -> new WorkTimeNotFound(id));

        workTimeRepository.delete(workTime);
    }

    @Override
    public WorkTime updateWorkTime(long id, WorkTimeDTO workTimeDTO) {
        return null;
    }

    @Override
    public WorkTime patchWorkTime(long id, WorkTimeDTO workTimeDTO) {
        return null;
    }

    // Helper methods
    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private GPSLocation createLocation(double latitude, double longitude) {
        return GPSLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    private void validateTimeDifference(Date requestTime) {
        var currentTime = new Date();
        var timeDiff = Math.abs(currentTime.getTime() - requestTime.getTime());

        // Example: If max allowable time diff is 5 minutes
        long maxAllowedDiff = 5 * 60 * 1000;
        if (timeDiff > maxAllowedDiff) {
            throw new IllegalArgumentException("Time difference exceeds the maximum allowable limit.");
        }
    }
}

