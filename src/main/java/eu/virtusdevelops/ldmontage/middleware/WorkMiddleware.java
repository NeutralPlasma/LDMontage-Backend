package eu.virtusdevelops.ldmontage.middleware;


import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.repositories.WorkSiteRepository;
import eu.virtusdevelops.ldmontage.repositories.WorkTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkMiddleware {
    private final WorkTimeRepository workTimeRepository;
    private final WorkSiteRepository workSiteRepository;


    public boolean canWorkAtLocation(long workLocation) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var workLocationOpt = workSiteRepository.findById(workLocation);
        if (workLocationOpt.isEmpty())
            return true;

        var location = workLocationOpt.get();

        return location.getAuthorizedWorkers().contains(user);
    }

}
