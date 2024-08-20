package eu.virtusdevelops.ldmontage.middleware;

import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.repositories.BreakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BreakMiddleware {
    private final BreakRepository breakRepository;

    public boolean canDelete(Long breakId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var breakOpt = breakRepository.findById(breakId);
        if(breakOpt.isEmpty()){return true;}
        var breakObj = breakOpt.get();

        // todo: compare who is the owner or if it has admin perms

        return true;
    }

    public boolean canUpdate(Long breakId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var breakOpt = breakRepository.findById(breakId);
        if(breakOpt.isEmpty()){return true;}
        var breakObj = breakOpt.get();

        // todo: check if user has admin perms?

        return true;
    }

}
