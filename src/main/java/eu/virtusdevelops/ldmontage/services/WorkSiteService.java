package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.exceptions.*;
import eu.virtusdevelops.ldmontage.domain.work.WorkSite;
import eu.virtusdevelops.ldmontage.repositories.UserRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkSiteRepository;
import eu.virtusdevelops.ldmontage.requests.WorkSiteCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkSiteService {
    private final WorkRepository workRepository;
    private final WorkSiteRepository workSiteRepository;
    private final UserRepository userRepository;

    public WorkSite createNew(WorkSiteCreateRequest request){
        var workOpt = workRepository.findById(request.workId());
        if(workOpt.isEmpty())
            throw new WorkNotFoundException(request.workId());

        var work = workOpt.get();

        var workSite = WorkSite.builder()
                .title(request.title())
                .description(request.description())
                .longitude(request.longitude())
                .latitude(request.latitude())
                .work(work)
                .build();

        return workSiteRepository.save(workSite);
    }


    public WorkSite addAuthorizedWorker(long worksiteId, UUID workerId){
        var worksiteOpt = workSiteRepository.findById(worksiteId);
        if(worksiteOpt.isEmpty())
            throw new WorkSiteNotFoundException(worksiteId);

        var userOpt = userRepository.findById(workerId);
        if(userOpt.isEmpty())
            throw new UserNotFoundException(workerId);


        var user = userOpt.get();
        var workSite = worksiteOpt.get();
        if(workSite.getAuthorizedWorkers().contains(user))
            throw new UserAlreadyAuthorizedOnWorksiteException(user.getId(), workSite.getId());


        workSite.getAuthorizedWorkers().add(user);
        workSite.setUpdatedAt(new Date());

        return workSiteRepository.save(workSite);
    }

    public WorkSite removeAuthorizedWorker(long worksiteId, UUID workerId){
        var worksiteOpt = workSiteRepository.findById(worksiteId);
        if(worksiteOpt.isEmpty())
            throw new WorkSiteNotFoundException(worksiteId);

        var userOpt = userRepository.findById(workerId);
        if(userOpt.isEmpty())
            throw new UserNotFoundException(workerId);


        var user = userOpt.get();
        var workSite = worksiteOpt.get();
        if(!workSite.getAuthorizedWorkers().contains(user))
            throw new UserNotAuthorizedOnWorksite(user.getId(), workSite.getId());


        workSite.getAuthorizedWorkers().remove(user);
        workSite.setUpdatedAt(new Date());

        return workSiteRepository.save(workSite);
    }


    public void deleteWorkSite(long worksiteId){
        var worksiteOpt = workSiteRepository.findById(worksiteId);
        if(worksiteOpt.isEmpty())
            throw new WorkSiteNotFoundException(worksiteId);

        workSiteRepository.delete(worksiteOpt.get());
    }

}
