package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.exceptions.*;
import eu.virtusdevelops.ldmontage.domain.user.User;
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

    public WorkSite createNew(WorkSiteCreateRequest request) {
        var work = workRepository.findById(request.workId())
                .orElseThrow(() -> new WorkNotFoundException(request.workId()));

        var workSite = WorkSite.builder()
                .title(request.title())
                .description(request.description())
                .longitude(request.longitude())
                .latitude(request.latitude())
                .work(work)
                .build();

        return workSiteRepository.save(workSite);
    }

    public WorkSite addAuthorizedWorker(long worksiteId, UUID workerId) {
        var workSite = getWorkSiteById(worksiteId);
        var user = getUserById(workerId);

        if (workSite.getAuthorizedWorkers().contains(user)) {
            throw new UserAlreadyAuthorizedOnWorksiteException(user.getId(), workSite.getId());
        }

        workSite.getAuthorizedWorkers().add(user);
        updateWorkSiteTimestamp(workSite);

        return workSiteRepository.save(workSite);
    }

    public WorkSite removeAuthorizedWorker(long worksiteId, UUID workerId) {
        var workSite = getWorkSiteById(worksiteId);
        var user = getUserById(workerId);

        if (!workSite.getAuthorizedWorkers().contains(user)) {
            throw new UserNotAuthorizedOnWorksite(user.getId(), workSite.getId());
        }

        workSite.getAuthorizedWorkers().remove(user);
        updateWorkSiteTimestamp(workSite);

        return workSiteRepository.save(workSite);
    }

    public void deleteWorkSite(long worksiteId) {
        var workSite = getWorkSiteById(worksiteId);
        workSiteRepository.delete(workSite);
    }

    // Helper methods
    private WorkSite getWorkSiteById(long worksiteId) {
        return workSiteRepository.findById(worksiteId)
                .orElseThrow(() -> new WorkSiteNotFoundException(worksiteId));
    }

    private User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void updateWorkSiteTimestamp(WorkSite workSite) {
        workSite.setUpdatedAt(new Date());
    }

}
