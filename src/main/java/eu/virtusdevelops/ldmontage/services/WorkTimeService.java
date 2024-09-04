package eu.virtusdevelops.ldmontage.services;


import eu.virtusdevelops.ldmontage.domain.exceptions.NoWorkTimeInProgressException;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorkSiteNotFoundException;
import eu.virtusdevelops.ldmontage.domain.exceptions.WorktimeAlreadyInProgressException;
import eu.virtusdevelops.ldmontage.domain.location.GPSLocation;
import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.domain.work.WorkTime;
import eu.virtusdevelops.ldmontage.repositories.UserRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkSiteRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkTimeRepository;
import eu.virtusdevelops.ldmontage.requests.WorkTimeEndRequest;
import eu.virtusdevelops.ldmontage.requests.WorkTimeStartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class WorkTimeService {
    private final WorkTimeRepository workTimeRepository;
    private final UserRepository userRepository;
    private final WorkSiteRepository workSiteRepository;
    private final WorkRepository workRepository;


    // todo authorize user can work at specific work loc
    @PreAuthorize("@workMiddleware.canWorkAtLocation(request.worksiteId())")
    public WorkTime startWork(WorkTimeStartRequest request){
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var workSiteOpt = workSiteRepository.findById(request.worksiteId());
        if(workSiteOpt.isEmpty())
            throw new WorkSiteNotFoundException(request.worksiteId());

        var worksite = workSiteOpt.get();

        var activeWorkOpt = workTimeRepository.findUserActiveWorks(user);

        if(activeWorkOpt.isEmpty())
            throw new WorktimeAlreadyInProgressException(user.getId());

        var currentTime = new Date();
        var timeDiff = currentTime.getTime() - request.time().getTime();

        // limit max time diff?
        var location = GPSLocation
                .builder()
                .longitude(request.longitude())
                .latitude(request.latitiude())
                .build();

        var workTime = WorkTime.builder()
                .user(user)
                .startLocation(location)
                .workSite(worksite)
                .startTime(request.time())
                .build();

        return workTimeRepository.save(workTime);

    }

    public WorkTime endWorkTime(WorkTimeEndRequest request){
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var currentWorkOpt = workTimeRepository.findUserActiveWorks(user);
        if(currentWorkOpt.isEmpty())
            throw new NoWorkTimeInProgressException();

        var currentWorkTime = currentWorkOpt.get();
        var location = GPSLocation
                .builder()
                .longitude(request.longitude())
                .latitude(request.latitiude())
                .build();

        var currentTime = new Date();
        var timeDiff = currentTime.getTime() - request.time().getTime();

        currentWorkTime.setEndLocation(location);
        currentWorkTime.setEndTime(request.time());
        
        return workTimeRepository.save(currentWorkTime);

    }

}
